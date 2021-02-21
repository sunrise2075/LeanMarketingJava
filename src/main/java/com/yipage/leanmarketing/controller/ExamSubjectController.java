package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/12.
 */
@RestController
@RequestMapping("/exam/subject")
public class ExamSubjectController {
    @Resource
    private ExamSubjectService examSubjectService;
    @Resource
    private ExamCategoryService examCategoryService;
    @Resource
    private ExamOrderService examOrderService;
    @Resource
    private UserService userService;
    @Resource
    private ExamQuestionsService examQuestionsService;

    @Resource
    private ExamOptionService examOptionService;


    @PostMapping("add")
    public Result add(ExamSubject examSubject) {
        examSubject.setCreateTime(new Date());
        examSubject.setUpdateTime(new Date());
        ExamCategory examCategory = examCategoryService.findById(examSubject.getCategoryId());
        if (examCategory != null) {
            examSubject.setCategoryName(examCategory.getName());
        }
        examSubjectService.save(examSubject);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        examSubjectService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(ExamSubject examSubject) {
        examSubject.setUpdateTime(new Date());
        examSubjectService.update(examSubject);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ExamSubject examSubject = examSubjectService.findById(id);
        return ResultGenerator.genSuccessResult(examSubject);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "categoryId", required = false) Integer categoryId,
                    @RequestParam(value = "isFree", required = false) Integer isFree,
                    @RequestParam(value = "isHide", required = false) Integer isHide,
                    @RequestParam(value = "openid", required = false) String openid,
                    @RequestParam(value = "isAppleDevice", required = false, defaultValue = "false") boolean isAppleDevice) {

        Map<String, Object> resMap = new HashMap<>();
        //查用户身份
        User user = null;
        if (openid != null) {
            user = userService.findBy("wxid", openid);
        }
        ExamCategory examCategory = new ExamCategory();
        examCategory.setIsHide(ExamCategory.IS_SHOW);
        List<ExamCategory> examCategoryList = examCategoryService.select(examCategory);
        resMap.put("examCategoryList", examCategoryList);
        String orderBy = "create_time desc,is_free ";
        PageHelper.startPage(page, limit, orderBy);

        Condition condition = new Condition(ExamSubject.class);
        Example.Criteria criteria = condition.createCriteria();
        if (isHide != null) {
            criteria.andCondition("is_hide =" + isHide);
        }

        //用户没有登录、没有绑定手机号码、用户从苹果设备访问小程序，只返回免费的考试
        if (user == null || !user.getIsBind().equals(User.IS_BIND_PHONE) || isAppleDevice) {
            criteria.andCondition("is_free =" + 1);
        } else if (isFree != null) {
            criteria.andCondition("is_free =" + isFree);
        }
        if (categoryId != null) {
            criteria.andCondition("category_id =" + categoryId);
        }
        List<ExamSubject> list = examSubjectService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);

        List<Map<String, Object>> list2 = packageExamSubject(list, user);

        pageInfo.setList(list2);
        resMap.put("status", 1);
        resMap.put("data", pageInfo.getList());
        resMap.put("count", pageInfo.getTotal());
        return resMap;
    }

    /**
     * 获取试题下面的推荐试题
     */
    @GetMapping("getRecommendExamSubject")
    public Result getRecommendExamSubject(@RequestParam(value = "subjectId", required = false) Integer subjectId,
                                          @RequestParam(defaultValue = "3") Integer limit) {
        List<ExamSubject> list = examSubjectService.getRecommendExamSubject(subjectId, limit);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 推荐试题(根据用户的标签记录)
     */
    @GetMapping("recommendedExams")
    public Result recommendedExams(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
                                   @RequestParam(value = "userId", required = false) Integer userId,
                                   @RequestParam(value = "isAppleDevice", required = false, defaultValue = "false") boolean isAppleDevice
    ) {

        Map<String, Object> map = examSubjectService.recommendedExams(page, limit, userId, isAppleDevice);
        packageExamSubject((List<ExamSubject>) map.get("data"), userService.findById(userId));
        List<Map<String, Object>> list = packageExamSubject((List<ExamSubject>) map.get("data"), userService.findById(userId));
        map.put("data", list);
        return ResultGenerator.genSuccessResult(map);
    }

    /**
     * 封装试题
     *
     * @param list
     * @param user
     */
    private List<Map<String, Object>> packageExamSubject(List<ExamSubject> list, User user) {
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (ExamSubject examSubject : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", examSubject.getId());

                ExamOption examOption = new ExamOption();
                examOption.setQuestionId(examSubject.getId());
                List<ExamOption> examOptions = examOptionService.select(examOption);
                map.put("examOptions", examOptions);
                map.put("categoryId", examSubject.getCategoryId());
                map.put("name", examSubject.getName());
                map.put("labels", examSubject.getLabels());
                map.put("abilitys", examSubject.getAbilitys());
                map.put("abilityNames", examSubject.getAbilityNames());
                map.put("exams", examSubject.getExams());
                map.put("isShare", examSubject.getIsShare());
                map.put("createTime", examSubject.getCreateTime());
                map.put("examDuration", examSubject.getExamDuration());
                map.put("type", examSubject.getType());
                map.put("isFree", examSubject.getIsFree());
                if (examSubject.getIsFree() == ExamSubject.IS_FREE) {
                    map.put("isBuy", 1);
                } else {
                    map.put("isBuy", 2);
                }
                map.put("codes", examSubject.getCodes());
                map.put("questionNum", examSubject.getQuestionNum());
                map.put("isHide", examSubject.getIsHide());
                map.put("price", examSubject.getPrice());
                map.put("updateTime", examSubject.getUpdateTime());
                ExamCategory category = examCategoryService.findById(examSubject.getCategoryId());
                if (category != null) {
                    map.put("categoryName", category.getName());
                }

                if (user != null) {

                    if (examSubject.getIsFree() == ExamSubject.IS_FREE) {
                        map.put("isBuy", 1);
                    } else {
                        if (StringUtils.isNotEmpty(examSubject.getCodes())) {
                            int menberLevel = user.getMemberLevel();
                            if (menberLevel == 1) {

                                //查找是否已经购买
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
                                    map.put("isBuy", 1);
                                } else {
                                    //查找是否已经购买
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

    /**
     * 后台数据查询
     *
     * @param categoryId
     * @return
     */
    @GetMapping("select")
    public Result select(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                         @RequestParam(value = "subjectId", required = false) Integer subjectId) {
        List<ExamSubject> list2 = new ArrayList<>();
        ExamSubject model = null;
        if (subjectId != null) {
            model = examSubjectService.findById(subjectId);
            //添加一个默认的(更新的时候),只有在分类相同的时候才添加
            if (model.getCategoryId() == categoryId) {
                list2.add(model);
            }
        }
        ExamSubject examSubject = new ExamSubject();
        examSubject.setCategoryId(categoryId);
        List<ExamSubject> list = examSubjectService.select(examSubject);

        if (!CollectionUtils.isEmpty(list)) {
            for (ExamSubject subject : list) {
                //查询这个试题下面已经添加了多少道题

                ExamQuestions examQuestions = new ExamQuestions();
                examQuestions.setSubjectId(subject.getId());
                Integer count = examQuestionsService.selectCount(examQuestions);
                if (count < subject.getQuestionNum()) {
                    list2.add(subject);
                }
            }
        }
        return ResultGenerator.genSuccessResult(list2);
    }

    /**
     * 根据标签查找对应的文库
     */
    @GetMapping("getByLabels")
    public Map getByLabels(String labels) {

        return examSubjectService.getByLabels(labels);
    }


}
