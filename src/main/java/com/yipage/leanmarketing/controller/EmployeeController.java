package com.yipage.leanmarketing.controller;

import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Employee;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.EmployeeService;
import com.yipage.leanmarketing.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/19.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Resource
    private EmployeeService employeeService;
    @Resource
    private UserService userService;

    @PostMapping("add")
    public Result add(Employee employee) {

        return dealResult(employeeService.save(employee));

        //查看此手机号是否存在于黑名单号里面
//        Black black = blackService.findBy("phone",employee.getPhone());
//        if(black != null){
//            return ResultGenerator.genFailResult("此手机号存在黑名单中");
//        }
//        //查看此手机号是否存在于代理商中
//        Agent agent = agentService.findBy("phone",employee.getPhone());
//        if(agent != null){
//            return ResultGenerator.genFailResult("此手机号存在代理商中");
//        }
//        User u = userService.findBy("phone",employee.getPhone());
//        if(u!=null){
//            return ResultGenerator.genFailResult("此手机号存在用户管理中");
//        }
//        Administrator a = administratorService.findBy("phone",employee.getPhone());
//        if(a !=null){
//            return ResultGenerator.genFailResult("此手机号存在管理员中");
//        }
//        Administrator administrator = (Administrator) request.getSession().getAttribute(Administrator.LOGIN_ADMIN_SESSION);
//        if(administrator ==null){
//            return ResultGenerator.genFailResult("登录的账号不存在");
//        }
//        User user = userService.findBy("phone",administrator.getUsername());
//        if(user == null){
//            return ResultGenerator.genFailResult("此企业不存在");
//        }
//        Employee model = employeeService.findBy("phone",employee.getPhone());
//        if(model!=null){
//            return ResultGenerator.genFailResult("此手机号已被存在");
//        }
//        //查看权益
//        UserBenefit userBenefit = userBenefitService.findById(user.getUserBenefitId());
//        if(userBenefit == null){
//            return ResultGenerator.genFailResult("企业权益不存在");
//        }
//        if(employee.getIdentity() == 1){ //添加的是总监身份
//
//            //查找有多少总监身份的员工
//            Employee employee1 = new Employee();
//            employee1.setUserId(user.getId());
//            employee1.setIdentity(1);
//
//            Integer count  = employeeService.selectCount(employee1);
//            if(count<userBenefit.getDirectorNum()){ //说明可以添加
//                employee.setCreateTime(new Date());
//                employee.setUpdateTime(new Date());
//                employee.setUserId(user.getId());
//                employeeService.save(employee);
//                return ResultGenerator.genSuccessResult();
//            }
//        }else{  //添加的是职员身份
//
//            //查找有多少总监身份的员工
//            Employee employee1 = new Employee();
//            employee1.setUserId(user.getId());
//            employee1.setIdentity(2);
//
//            Integer count  = employeeService.selectCount(employee1);
//            if(count<userBenefit.getClerkNum()){ //说明可以添加
//                employee.setCreateTime(new Date());
//                employee.setUpdateTime(new Date());
//                employee.setUserId(user.getId());
//                employeeService.save(employee);
//                return ResultGenerator.genSuccessResult();
//            }
//        }
//        return ResultGenerator.genFailResult("添加失败");
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        employeeService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(Employee employee) {

        return dealResult(employeeService.update(employee));

//        //查看此手机号是否存在于黑名单号里面
//        Black black = blackService.findBy("phone",employee.getPhone());
//        if(black != null){
//            return ResultGenerator.genFailResult("此手机号存在黑名单中");
//        }
//        User u = userService.findBy("phone",employee.getPhone());
//        if(u!=null){
//            return ResultGenerator.genFailResult("此手机号存在用户管理中");
//        }
//        Administrator a = administratorService.findBy("phone",employee.getPhone());
//        if(a !=null){
//            return ResultGenerator.genFailResult("此手机号存在管理员中");
//        }
//        //查看此手机号是否存在于代理商中
//        Agent agent = agentService.findBy("phone",employee.getPhone());
//        if(agent != null){
//            return ResultGenerator.genFailResult("此手机号存在于代理商中");
//        }
//        //查找员工信息
//        Employee model = employeeService.findById(employee.getId());
//        if(!model.getPhone().equals(employee.getPhone())){  //手机号不相同
//            //手机号不同则查数据库
//            Employee  employee1 = employeeService.findBy("phone",employee.getPhone());
//            if(employee1 !=null){  //说明数据库里面存在
//                return ResultGenerator.genFailResult("此手机号已存在员工信息里面");
//            }
//        }
//        employee.setUpdateTime(new Date());
//        return ResultGenerator.genSuccessResult(employeeService.update(employee));
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Employee employee = employeeService.findById(id);
        return ResultGenerator.genSuccessResult(employee);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "companyName", required = false) String companyName,
                    @RequestParam(value = "openid", required = false) String openid,
                    @RequestParam(value = "userId", required = false) Integer userId) {

        User user = null;
        if (openid != null) {
            user = userService.findBy("wxid", openid);
        }
        if (user != null) {
            userId = user.getId();
        }
        return employeeService.listPagerToEmployee(page, limit, userId, companyName);
    }

    private Result dealResult(Integer result) {
        if (result == 1) {
            return ResultGenerator.genSuccessResult();
        } else if (result == 2) {
            return ResultGenerator.genFailResult("此手机存在黑名单里面");
        } else if (result == 3) {
            return ResultGenerator.genFailResult("此手机存在子用户表中");
        } else if (result == 4) {
            return ResultGenerator.genFailResult("此手机存在员工管理中");
        } else if (result == 5) {
            return ResultGenerator.genFailResult("此手机存在代理商中");
        } else if (result == 6) {
            return ResultGenerator.genFailResult("此手机存在经销商中");
        } else if (result == 7) {
            return ResultGenerator.genFailResult("此手机存在经销商中");
        } else if (result == 8) {
            return ResultGenerator.genFailResult("不是企业会员登录的");
        } else if (result == 9) {
            return ResultGenerator.genFailResult("员工身份已满");
        } else {
            return ResultGenerator.genFailResult("总监身份已满");
        }
    }
}
