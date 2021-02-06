package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.EmployeeService;
import com.yipage.leanmarketing.service.UserService;
import com.yipage.leanmarketing.service.VideoService;
import com.yipage.leanmarketing.service.WatchRecordService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/17.
 */
@RestController
@RequestMapping("/watch/record")
public class WatchRecordController {
    @Resource
    private WatchRecordService watchRecordService;
    @Resource
    private VideoService videoService;

    @Resource
    private UserService userService;
    @Resource
    private EmployeeService employeeService;
    @Resource
    private HttpServletRequest request;

    @PostMapping("add")
    @NeedLoginAnnotation
    public Result add(@RequestBody WatchRecord watchRecord) {

        return ResultGenerator.genSuccessResult(watchRecordService.save(watchRecord));
    }

    @DeleteMapping("/{ids}")
    public Result deletes(@PathVariable String ids) {
        watchRecordService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("delete/{ids}")
    public Result deletes2(@PathVariable String ids) {
        watchRecordService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody WatchRecord watchRecord) {
        watchRecordService.update(watchRecord);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        WatchRecord watchRecord = watchRecordService.findById(id);
        return ResultGenerator.genSuccessResult(watchRecord);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "openid", required = false) String openid) {

        //条件
        WatchRecord watchRecord = new WatchRecord();

        if (openid != null) {
            User user = userService.findBy("wxid", openid);
            if (user != null) {
                watchRecord.setOpenid(user.getWxid());
            }
        }
        //排序
        String orderBy = "update_time desc";
        //分页
        PageHelper.startPage(page, limit, orderBy);

        //查询
        List<WatchRecord> list = watchRecordService.select(watchRecord);
        PageInfo pageInfo = new PageInfo(list);
        //获得结果集
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 查询企业员工的观看记录
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("list2")
    public Map list2(@RequestParam(defaultValue = "1") Integer page,
                     @RequestParam(defaultValue = "10") Integer limit,
                     @RequestParam(required = false) Integer type) {
        PageHelper.startPage(page, limit, "create_time desc");
        PageInfo pageInfo = null;
        if (type == 1) {
            List<WatchRecord> all = watchRecordService.findAll();
            pageInfo = new PageInfo(all);
        } else {

            Administrator administrator = (Administrator) request.getSession().getAttribute(Administrator.LOGIN_ADMIN_SESSION);

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
                List<WatchRecord> records = watchRecordService.findByCondition(condition);
                pageInfo = new PageInfo(records);

                if (!CollectionUtils.isEmpty(records)) {

                    for (WatchRecord record : records) {
                        Map<String, Object> map = new HashMap<>();

                        Video video = videoService.findById(record.getVideoId());
                        if (video != null) {
                            map.put("videoName", video.getTitle());
                        }
                        User user1 = userService.findBy("wxid", record.getOpenid());

                        if (user1 != null) {
                            //                        Employee employee1 = employeeService.findBy("phone",user1.getPhone());
                            //                        if(employee1!=null){
                            //                            map.put("employeeName",employee1.getName());
                            //                        }
                            map.put("userName", user1.getName());
                        }
                        map.put("id", record.getId());
                        map.put("createTime", record.getCreateTime());
                        map.put("updateTime", record.getUpdateTime());
                        list2.add(map);
                    }
                }
                pageInfo.setList(list2);
            }
        }
        return MapUtil.PageResult(pageInfo);
    }
}
