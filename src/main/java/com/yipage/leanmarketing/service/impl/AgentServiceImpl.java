package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.*;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.AgentService;
import com.yipage.leanmarketing.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class AgentServiceImpl extends AbstractService<Agent> implements AgentService {
    @Resource
    private AgentMapper agentMapper;
    @Resource
    private BlackMapper blackMapper;
    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserSubMapper userSubMapper;

    @Resource
    private DealerMapper dealerMapper;

    @Resource
    private AdministratorMapper administratorMapper;

    @Override
    public Integer save(Agent model) {

        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        Integer result = judgingPhone(model.getPhone());
        if (result == 1) {
            //查看一下此手机号有没有被注册过了
            Agent agent = new Agent();
            agent.setPhone(model.getPhone());
            Agent agent1 = agentMapper.selectOne(agent);
            if (agent1 != null) {
                return 5;
            }
            //查找代理区域是否有重复的
            Agent agent2 = new Agent();
            agent2.setProvince(model.getProvince());
            Agent agent3 = agentMapper.selectOne(agent2);
            if (agent3 != null) {
                return 7;
            } else {
                //可以添加
                agentMapper.insert(model);
                //添加后台管理员信息
                Administrator administrator = new Administrator();
                administrator.setRoleCode(Administrator.AGENT_ROLE);
                Role role = new Role();
                role.setUniqueCode(Administrator.AGENT_ROLE);
                role = roleMapper.selectOne(role);
                administrator.setRoleId(role.getId());
                administrator.setIdentityId(model.getId());
                administrator.setHeadPortrait(LeanMarkentingConstant.headImg);
                administrator.setRoleName(role.getName());
                administrator.setAddress(model.getProvince());
                administrator.setCreateTime(new Date());
                administrator.setUpdateTime(new Date());
                administrator.setUsername(model.getPhone());
                administrator.setPhone(model.getPhone());
                administrator.setPassword(MD5Util.MD5(model.getPhone()));
                administratorMapper.insertSelective(administrator);
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

                Integer agentId = Integer.parseInt(s);
                Agent originAgent = agentMapper.selectByPrimaryKey(agentId);

                //删除代理商
                agentMapper.deleteByPrimaryKey(agentId);

                //删除管理员
                Administrator queryAdministrator = new Administrator();
                queryAdministrator.setIdentityId(agentId);
                queryAdministrator.setRoleCode(Administrator.AGENT_ROLE);

                Administrator originAdministrator = administratorMapper.selectOne(queryAdministrator);
                if (originAdministrator != null) {
                    administratorMapper.deleteByPrimaryKey(originAdministrator.getId());
                }

                //修改会员信息
                User queryUser = new User();
                queryUser.setPhone(originAgent.getPhone());
                User originUser = userMapper.selectOne(queryUser);
                if (originUser != null) {

                    updateUserMember(originUser);

//                    User updateUser = new User();
//                    updateUser.setId(originUser.getId());
//                    updateUser.setIsBind(User.NO_BIND_PHONE);
//                    updateUser.setIdentity(User.USER_MENNBER_IDENTITY);
//                    updateUser.setPhone("123456");
//                    updateUser.setExpirationTime(DateUtil.addTime(new Date(),100));
//                    updateUser.setMemberLevel(-1);
//                    updateUser.setName("");
//                    updateUser.setCompanyName("");
//                    updateUser.setAgentProvince("");
//                    updateUser.setSuperiorId(-1);
//                    userMapper.updateByPrimaryKeySelective(updateUser);
//                    //删除子用户
//                    UserSub querySub = new UserSub();
//                    querySub.setPhone(originAgent.getPhone());
//                    List<UserSub> select = userSubMapper.select(querySub);
//
//                    userSubMapper.deleteByPrimaryKey(select.get(0).getId());
                }

            }
        }
    }

    @Override
    public Integer update(Agent model) {
        model.setUpdateTime(new Date());
        //判断手机号是否存在于其他表中(固定的几张表)
        Integer result = judgingPhone(model.getPhone());

        if (result == 1) { //表示不存在

            Agent agent = agentMapper.selectByPrimaryKey(model.getId());
            if (agent != null) {
                //比较手机号是否相同
                if (model.getPhone().equals(agent.getPhone())) {
                    //相同
                    //再判断代理区域
                    result = judgeAgentArea(agent.getProvince(), model);
                } else {

                    //判断手机号是否存在于代理商中
                    Agent agent1 = new Agent();
                    agent1.setPhone(model.getPhone());
                    Agent agent2 = agentMapper.selectOne(agent1);
                    if (agent2 != null) {
                        //说明存在于代理商中
                        return 5;
                    } else {
                        result = judgeAgentArea(agent.getProvince(), model);
                        if (result == 1) {
                            //更新管理员信息(因为手机号改变了)
                            Administrator administrator = new Administrator();
                            administrator.setPhone(agent.getPhone());
                            Administrator administrator2 = administratorMapper.selectOne(administrator);
                            if (administrator2 != null) {
                                administrator.setId(administrator2.getId());
                                administrator.setPhone(model.getPhone());
                                administrator.setUpdateTime(new Date());
                                administratorMapper.updateByPrimaryKeySelective(administrator);
                            }
                        }
                    }
                }

            }
        }
        return result;
    }

    /**
     * 判断代理区域
     */
    private Integer judgeAgentArea(String province, Agent model) {

        if (model.getProvince().equals(province)) {
            //代理区域与原来相同
            agentMapper.updateByPrimaryKeySelective(model);
            return 1;
        } else {
            Agent agent = new Agent();
            agent.setProvince(model.getProvince());
            Agent agent1 = agentMapper.selectOne(agent);
            if (agent1 != null) {
                //说明这个省份的代理区域有了
                return 7;
            } else {
                agentMapper.updateByPrimaryKeySelective(model);
                return 1;
            }
        }
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
            return 8;
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

        //查看此手机号是否存在于经销商中
        Dealer dealer = new Dealer();
        dealer.setPhone(phone);
        Dealer dealer1 = dealerMapper.selectOne(dealer);
        if (dealer1 != null) {
            return 6;
        }
        return 1;
    }

    @Override
    public List<Agent> select(Agent agent) {
        return agentMapper.select(agent);
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
