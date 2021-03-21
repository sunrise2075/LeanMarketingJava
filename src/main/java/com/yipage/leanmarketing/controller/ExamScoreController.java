package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.*;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@RestController
@RequestMapping("/exam/score")
public class ExamScoreController {
    @Resource
    private ExamScoreService examScoreService;
    @Resource
    private ExamSubjectService examSubjectService;
    @Resource
    private UserService userService;


    @Resource
    private HttpServletRequest request;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private ExamOrderService examOrderService;


    @PostMapping("add")
    public Result add(@RequestBody ExamScore score) {
        User user = userService.findBy("wxid", score.getOpenid());
        if (user != null) {
            score.setUserName(user.getNickname());
            score.setUserId(user.getId());
        }
        ExamSubject subject = examSubjectService.findById(score.getSubjectId());
        if (subject != null) {
            score.setSubjectName(subject.getName());
        }
        examScoreService.save(score);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("examScore", score);

        List<ExamSubject> examSubjectList = (List<ExamSubject>) examSubjectService.recommendedExams(1, 2, user.getId(), false).get("data");
        List<Map<String, Object>> list2 = packageExamSubject(examSubjectList, user);
        resMap.put("examSubjectList", list2);
        return ResultGenerator.genSuccessResult(resMap);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        examScoreService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody ExamScore examScore) {
        examScoreService.update(examScore);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ExamScore examScore = examScoreService.findById(id);
        return ResultGenerator.genSuccessResult(examScore);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "examName", required = false) String examName,
                    @RequestParam(value = "userName", required = false) String userName,
                    @RequestParam(value = "openid", required = false) String openid) {

        String orderBy = "create_time desc";
        PageHelper.startPage(page, limit, orderBy);

        Condition condition = new Condition(ExamScore.class);
        Example.Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotEmpty(examName)) {
            criteria.andCondition("subject_name like  '%" + examName + "%'");
        }
        if (StringUtils.isNotEmpty(userName)) {
            criteria.andCondition("user_name like  '%" + userName + "%'");
        }
        if (StringUtils.isNotEmpty(openid)) {
            criteria.andCondition("openid = '" + openid + "'");
        }
        List<ExamScore> list = examScoreService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
//        List<Map<String,Object>> list2 = new ArrayList<>();
//        if(!CollectionUtils.isEmpty(list)){
//            for (ExamScore examScore : list) {
//                Map<String,Object> map = new HashMap<>();
//                map.put("id",examScore.getId());
//                map.put("openid",examScore.getOpenid());
//                map.put("score",examScore.getScore());
//                map.put("subjectId",examScore.getSubjectId());
//                map.put("createTime",examScore.getCreateTime());
//                map.put("evaluate",examScore.getEvaluate());
//                map.put("updateTime",examScore.getUpdateTime());
//
//                User user = userService.findBy("wxid",examScore.getOpenid());
//                if(user != null){
//                    map.put("userName",user.getNickname());
//                }
//
//                ExamSubject examSubject = examSubjectService.findById(examScore.getSubjectId());
//                if(examSubject!=null){
//                    map.put("subjectName",examSubject.getName());
//                }
//                list2.add(map);
//            }
//        }
//        pageInfo.setList(list2);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 查询企业员工的考试记录
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list2")
    public Map list2(@RequestParam(defaultValue = "1") Integer page,
                     @RequestParam(defaultValue = "10") Integer limit) {


        Administrator administrator = (Administrator) request.getSession().getAttribute(Administrator.LOGIN_ADMIN_SESSION);
        PageInfo pageInfo = null;
        if (administrator != null) {

            //找到用户企业
            User user = userService.findBy("phone", administrator.getUsername());
            //找到这个企业下的所有员工
            Employee employee = new Employee();
            employee.setUserId(user.getId());
            List<Employee> employees = employeeService.select(employee);
            String openids = "";
            if (!CollectionUtils.isEmpty(employees)) {

                for (Employee employee1 : employees) {
                    User user1 = userService.findBy("phone", employee1.getPhone());
                    if (user1 != null) {
                        openids = openids + user1.getWxid() + ",";
                    }
                }
            }
            List<Map<String, Object>> list2 = new ArrayList<>();

            if (openids.length() > 0) {
                openids = openids.substring(0, openids.length() - 1);
            } else {
                pageInfo = new PageInfo(list2);
                return MapUtil.PageResult(pageInfo);
            }
            Condition condition = new Condition(WatchRecord.class);
            Example.Criteria criteria = condition.createCriteria();
            criteria.andCondition("openid in('" + openids + "')");
            List<ExamScore> list = examScoreService.findByCondition(condition);
            pageInfo = new PageInfo(list);


            pageInfo.setList(list);
        }
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 获取前七天的平均得分的数据和考试成绩的分页
     */
    @GetMapping("getExamScoreInfo")
    public Map getExamScoreInfo(@RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer limit,
                                @RequestParam(value = "openid", required = false) String openid) {

        //查用户身份
        User user = null;
        if (openid != null) {
            user = userService.findBy("wxid", openid);
        }

        //考试成绩分页
        Map<String, Object> map = this.list(page, limit, null, null, openid);
        //获取前七天的平均得分的数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //时间数组
        String[] timeArr = new String[7];
        //平均分数数组(保留整数)
        Integer[] averageScoreArr = new Integer[7];

        for (int i = 6; i >= 0; i--) {
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            date = calendar.getTime();
            Integer registerNum = examScoreService.getAverageByDay(sdf.format(date), openid);
            if (registerNum == null) {
                registerNum = 0;
            }
            timeArr[6 - i] = sdf.format(date);
            averageScoreArr[6 - i] = registerNum;
        }
        //获取这周的最高分
        Integer highestScore = examScoreService.getHighestScore(timeArr[0], timeArr[6], openid);
        map.put("highestScore", highestScore);

        map.put("timeArr", timeArr);
        map.put("averageScoreArr", averageScoreArr);

        //随机推荐
        List<ExamSubject> list = examSubjectService.getRecommendExamSubject(null, 3);
        List<Map<String, Object>> list2 = packageExamSubject(list, user);
        map.put("examSubjectList", list2);
        return map;
    }


    @GetMapping("getByDay")
    public Map getByDay(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue = "10") Integer limit,
                        @RequestParam(value = "day") String day,
                        @RequestParam(value = "openid", required = false) String openid
    ) {

        Map<String, Object> resMap = examScoreService.getByDay(page, limit, day, openid);
        return resMap;
    }

    /**
     * 封装试题
     */
    private List<Map<String, Object>> packageExamSubject(List<ExamSubject> list, User user) {
        List<Map<String, Object>> list2 = new ArrayList<>();

        if (!CollectionUtils.isEmpty(list)) {
            for (ExamSubject examSubject : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", examSubject.getId());
                map.put("categoryId", examSubject.getCategoryId());
                map.put("name", examSubject.getName());
                map.put("createTime", examSubject.getCreateTime());
                map.put("examDuration", examSubject.getExamDuration());
                map.put("isFree", examSubject.getIsFree());
                map.put("codes", examSubject.getCodes());
                map.put("questionNum", examSubject.getQuestionNum());
                map.put("isHide", examSubject.getIsHide());
                map.put("price", examSubject.getPrice());
                map.put("updateTime", examSubject.getUpdateTime());

                if (user != null) {

                    if (examSubject.getIsFree() == ExamSubject.IS_FREE) {
                        map.put("isBuy", 1);
                    } else {
                        if (StringUtils.isNotEmpty(examSubject.getCodes())) {

                            int menberLevel = user.getMemberLevel();
                            if (menberLevel == 1) {

                                ExamOrder order = new ExamOrder();
                                order.setOpenid(user.getWxid());
                                order.setPayState(VideoOrder.ISPAY);
                                order.setSubjectId(examSubject.getId());
                                List<ExamOrder> orderList = examOrderService.select(order);
                                if (!CollectionUtils.isEmpty(orderList)) {
                                    map.put("isBuy", 1);
                                } else {
                                    map.put("isBuy", 2);
                                }

                            } else {

                                if (menberLevel == 10) {  //添加了一个经销商,前端页面只有0-9
                                    menberLevel = 1;
                                }
                                if (examSubject.getCodes().contains(menberLevel + "")) {
                                    map.put("isFree", Library.IS_FREE);
                                    map.put("isBuy", 1);

                                } else {
                                    ExamOrder order = new ExamOrder();
                                    order.setOpenid(user.getWxid());
                                    order.setPayState(VideoOrder.ISPAY);
                                    order.setSubjectId(examSubject.getId());
                                    List<ExamOrder> orderList = examOrderService.select(order);
                                    if (!CollectionUtils.isEmpty(orderList)) {
                                        map.put("isBuy", 1);
                                    } else {
                                        map.put("isBuy", 2);
                                    }
                                }

                            }

                        }
                    }
                }
                list2.add(map);
            }
        }
        return list2;
    }
}
