package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.*;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.EmployeeService;
import com.yipage.leanmarketing.service.UserBenefitService;
import com.yipage.leanmarketing.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/19.
 */
@Service
@Transactional
public class EmployeeServiceImpl extends AbstractService<Employee> implements EmployeeService {
    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private BlackMapper blackMapper;

    @Resource
    private UserSubMapper userSubMapper;

    @Resource
    private AgentMapper agentMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private AdministratorMapper administratorMapper;

    @Resource
    private DealerMapper dealerMapper;

    @Resource
    private UserService userService;
    @Resource
    private UserBenefitService userBenefitService;

    @Resource
    private HttpServletRequest request;

    @Override
    public Integer selectCount(Employee employee) {
        return employeeMapper.selectCount(employee);
    }

    @Override
    public Integer save(Employee model) {

        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        Integer result = judgingPhone(model.getPhone());

        if (result == 1) {

            Employee employee = new Employee();
            employee.setPhone(model.getPhone());

            Employee employee1 = employeeMapper.selectOne(employee);
            if (employee1 != null) {
                return 4;
            } else {
//                //查找企业身份
//                Administrator administrator = (Administrator) request.getSession().getAttribute(Administrator.LOGIN_ADMIN_SESSION);
//                if(administrator!=null){
                //查企业
                User user = userService.findById(model.getUserId());
                if (user != null) {

                    Integer count = 0;
                    //查权益信息
                    UserBenefit userBenefit = userBenefitService.findById(user.getUserBenefitId());
                    if (userBenefit != null) {

                        if (model.getIdentity() == 1) { //添加的是总监身份

                            Employee employee2 = new Employee();
                            employee2.setUserId(user.getId());
                            employee2.setIdentity(1);

                            count = employeeMapper.selectCount(employee2);
                            if (count < userBenefit.getDirectorNum()) { //说明可以添加

                                model.setUserId(user.getId());
                                employeeMapper.insertSelective(model);
                                return 1;
                            } else {
                                return 10;
                            }
                        } else {    //添加的是员工身份

                            //查找有多少总监身份的员工
                            Employee employee3 = new Employee();
                            employee3.setUserId(user.getId());
                            employee3.setIdentity(2);

                            count = employeeMapper.selectCount(employee3);
                            if (count < userBenefit.getClerkNum()) { //说明可以添加

                                model.setUserId(user.getId());
                                employeeMapper.insertSelective(model);
                                return 1;
                            } else {
                                return 9;
                            }
                        }
                    }
//                    }else{
//                        //登录的用户不存在
//                        return 8;
//                    }
                }

            }
        }
        return result;
    }

    @Override
    public Integer update(Employee model) {

        model.setUpdateTime(new Date());

        Integer result = judgingPhone(model.getPhone());
        if (result == 1) {

            //查看员工信息
            Employee employee = employeeMapper.selectByPrimaryKey(model.getId());
            if (employee != null) {

                if (model.getPhone().equals(employee.getPhone())) {//手机号相同

                    employeeMapper.updateByPrimaryKeySelective(model);
                    return 1;
                } else {
                    Employee employee1 = new Employee();
                    employee1.setPhone(model.getPhone());
                    Employee employee2 = employeeMapper.selectOne(employee1);
                    if (employee2 != null) {
                        //已存在此用户
                        return 4;
                    } else {
                        employeeMapper.updateByPrimaryKeySelective(model);
                        return 1;
                    }
                }
            }
        }
        return super.update(model);
    }

    @Override
    public Map listPagerToEmployee(Integer page, Integer limit, Integer userId, String companyName) {

        Map<String, Object> resMap = new HashMap<>();
        Integer beginIndex = (page - 1) * limit;
        Integer count = employeeMapper.countToEmployee(userId, companyName);
        resMap.put("count", count);
        List<Map<String, Object>> list = employeeMapper.listPagerToEmployee(limit, beginIndex, userId, companyName);
        resMap.put("data", list);
        resMap.put("status", 1);
        return resMap;
    }

    /**
     * 判断手机号是否存在
     *
     * @param phone
     * @return
     */
    private Integer judgingPhone(String phone) {

        //查看此手机号是否存在于黑名单号里面
        Black black = new Black();
        black.setPhone(phone);
        Black black1 = blackMapper.selectOne(black);
        if (black1 != null) {
            return 2;
        }
        //查看是否存在于子用户表里面
        UserSub userSub = new UserSub();
        userSub.setPhone(phone);
        UserSub userSub1 = userSubMapper.selectOne(userSub);
        if (userSub1 != null) {
            return 3;
        }

        //查看此手机号是否存在于员工管理
        Dealer dealer = new Dealer();
        dealer.setPhone(phone);
        Dealer dealer1 = dealerMapper.selectOne(dealer);
        if (dealer1 != null) {
            return 6;
        }

        //查看此手机号是否存在于代理商中
        Agent agent = new Agent();
        agent.setPhone(phone);
        Agent agent1 = agentMapper.selectOne(agent);
        if (agent1 != null) {
            return 5;
        }
        return 1;
    }

    @Override
    public List<Employee> select(Employee employee) {
        return employeeMapper.select(employee);
    }
}
