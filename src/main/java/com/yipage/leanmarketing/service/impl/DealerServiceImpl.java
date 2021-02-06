package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.*;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.DealerService;
import com.yipage.leanmarketing.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * Created by CodeGenerator on 2019/03/19.
 */
@Service
@Transactional
public class DealerServiceImpl extends AbstractService<Dealer> implements DealerService {

    @Resource
    private DealerMapper dealerMapper;

    @Resource
    private BlackMapper blackMapper;
    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserSubMapper userSubMapper;

    @Resource
    private AgentMapper agentMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private AdministratorMapper administratorMapper;

    @Override
    public Integer save(Dealer model) {

        Integer result = judgingPhone(model.getPhone());

        if (result == 1) {

            //查看此手机号是否在经销商中
            Dealer dealer = new Dealer();
            dealer.setPhone(model.getPhone());
            Dealer dealer1 = dealerMapper.selectOne(dealer);
            if (dealer1 != null) {
                return 6;
            } else {
                model.setCreateTime(new Date());
                model.setUpdateTime(new Date());
                super.save(model);

                addDealerAdministrator(model);
                return 1;
            }
        }
        return result;
    }

    @Override
    public void deleteByIds(String ids) {

        String[] split = ids.split(",");
        if (split.length > 0) {
            for (String s : split) {

                Integer dealerId = Integer.parseInt(s);
                Dealer originDealer = dealerMapper.selectByPrimaryKey(dealerId);

                //删除经销商
                dealerMapper.deleteByPrimaryKey(dealerId);

                //删除管理员
                Administrator queryAdministrator = new Administrator();
                queryAdministrator.setIdentityId(dealerId);
                queryAdministrator.setRoleCode(Administrator.Dealer_ROLE);

                Administrator originAdministrator = administratorMapper.selectOne(queryAdministrator);
                if (originAdministrator != null) {
                    administratorMapper.deleteByPrimaryKey(originAdministrator.getId());
                }

                //修改会员信息
                User queryUser = new User();
                queryUser.setPhone(originDealer.getPhone());
                User originUser = userMapper.selectOne(queryUser);
                if (originUser != null) {

                    updateUserMember(originUser);

//                    User updateUser = new User();
//                    updateUser.setId(originUser.getId());
//                    updateUser.setIsBind(User.NO_BIND_PHONE);
//                    updateUser.setIdentity(User.USER_MENNBER_IDENTITY);
//                    updateUser.setPhone("123456");
//                    updateUser.setMemberLevel(-1);
//                    updateUser.setName("");
//                    updateUser.setCompanyName("");
//                    updateUser.setAgentProvince("");
//                    updateUser.setSuperiorId(-1);
//                    updateUser.setExpirationTime(DateUtil.addTime(new Date(),100));
//                    userMapper.updateByPrimaryKeySelective(updateUser);
//                    //删除子用户
//
//                    UserSub querySub = new UserSub();
//                    querySub.setPhone(originDealer.getPhone());
//                    List<UserSub> select = userSubMapper.select(querySub);
//
//                    userSubMapper.deleteByPrimaryKey(select.get(0).getId());

                }
            }
        }
    }

    @Override
    public Integer update(Dealer model) {

        model.setUpdateTime(new Date());
        Integer result = judgingPhone(model.getPhone());
        if (result == 1) {

            //查原来的手机号
            Dealer dealer = dealerMapper.selectByPrimaryKey(model.getId());
            if (dealer != null) {

                if (model.getPhone().equals(dealer.getPhone())) {
                    //手机号相同
                    return super.update(model);
                } else {
                    //手机号不同则根据手机号查询
                    Dealer dealer1 = new Dealer();
                    dealer1.setPhone(model.getPhone());
                    Dealer dealer2 = dealerMapper.selectOne(dealer1);

                    if (dealer2 != null) {
                        return 6;
                    } else {
                        return super.update(model);
                    }
                }
            }
        }
        return result;
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
        //查看是否存在于用户表中
        User user = new User();
        user.setPhone(phone);
        User user1 = userMapper.selectOne(user);
        if (user1 != null) {
            return 7;
        }
        //查看是否存在于子用户表里面
        UserSub userSub = new UserSub();
        userSub.setPhone(phone);
        UserSub userSub1 = userSubMapper.selectOne(userSub);
        if (userSub1 != null) {
            return 3;
        }

        //查看此手机号是否存在于员工管理
        Employee employee = new Employee();
        employee.setPhone(phone);
        Employee employee1 = employeeMapper.selectOne(employee);
        if (employee1 != null) {
            return 4;
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

    /**
     * 添加经销商管理员身份
     *
     * @param model
     */
    private void addDealerAdministrator(Dealer model) {

        //经销商需不需要登录后台,需要的话就添加后台管理员信息
        Administrator administrator = new Administrator();
        administrator.setRoleCode(Administrator.Dealer_ROLE);
        Role role = new Role();
        role.setUniqueCode(Administrator.Dealer_ROLE);
        role = roleMapper.selectOne(role);
        administrator.setPhone(model.getPhone());
        administrator.setRoleName(role.getName());
        administrator.setHeadPortrait(LeanMarkentingConstant.headImg);
        administrator.setRoleId(role.getId());
        administrator.setIdentityId(model.getId());
        administrator.setCreateTime(new Date());
        administrator.setUpdateTime(new Date());
        administrator.setUsername(model.getPhone());
        administrator.setPassword(MD5Util.MD5(model.getPhone()));
        administratorMapper.insertSelective(administrator);
    }

    /**
     * 变成普通会员并删除子用户
     *
     * @param user
     */
    public void updateUserMember(User user) {

        String phone = user.getPhone();

        user.setIsBind(User.NO_BIND_PHONE);
        user.setIdentity(User.USER_MENNBER_IDENTITY);
        user.setPhone(null);
        user.setMemberLevel(1);
        user.setUserBenefitId(null);
        user.setExpirationTime(null);
        user.setName(null);
        user.setCompanyName(null);
        user.setAgentProvince(null);
        user.setSuperiorId(null);
        userMapper.updateByPrimaryKey(user);
        //删除子用户
        UserSub querySub = new UserSub();
        querySub.setPhone(phone);
        UserSub originSub = userSubMapper.selectOne(querySub);

        if (originSub != null) {
            userSubMapper.deleteByPrimaryKey(originSub.getId());
        }
    }
}
