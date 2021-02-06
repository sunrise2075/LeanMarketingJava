package com.yipage.leanmarketing.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.controller.UserController;
import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.mapper.*;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.UserService;
import com.yipage.leanmarketing.utils.MD5Util;
import com.yipage.leanmarketing.utils.TokenManagerUtil;
import com.yipage.leanmarketing.utils.WeiXinUtil;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private HttpServletRequest request;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private AdministratorMapper administratorMapper;
    @Resource
    private UserSubMapper userSubMapper;
    @Resource
    private UserBenefitMapper userBenefitMapper;

    @Resource
    private DealerMapper dealerMapper;

    @Resource
    private AgentMapper agentMapper;

    @Resource
    private EmployeeMapper employeeMapper;

    /**
     * 用户微信授权登录
     */
    @Override
    public User weixinAuthority(String code, String headimgurl, String nickname, String province, String city, Integer superiorId) {

        JSONObject jsonObject = WeiXinUtil.weixinAuthority(code);
        logger.info("微信验证身份接口调用返回内容：{}", jsonObject.toJSONString());
        User user = new User();

        if (jsonObject != null) {
            //查找是否存在此用户
            user.setWxid(jsonObject.getString("openid"));
            List<User> userList = userMapper.select(user);
            //微信网页授权回调两次问题,让线程停一秒
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (CollectionUtils.isEmpty(userList)) {
                //保存用户
                user.setWxid(jsonObject.getString("openid"));
                user.setHeadPortrait(headimgurl);
                user.setNickname(nickname);
                user.setSource(1); //微信端
                user.setRegisteredAddress(province);
                user.setSuperiorId(superiorId);
                user.setIdentity(User.USER_MENNBER_IDENTITY);
                user.setCreateTime(new Date());
                user.setUpdateTime(new Date());
                user.setIsFirst(User.IS_FIRST);
                userMapper.insertSelective(user);
                request.getSession().setAttribute(User.LOGIN_USER_SESSION, user);
            } else {
                user = userList.get(0);
            }
            user.setToken(TokenManagerUtil.createToken(user.getId()));
            user.setSessionKey(jsonObject.getString("session_key"));
        }
        return user;
    }

    @Override
    public List<User> exportExcel(String identity, String memberLevel, String address, String superiorId) {
        return userMapper.exportExcel(identity, memberLevel, address, superiorId);
    }

    @Override
    public List<User> select(User user) {
        return userMapper.select(user);
    }


    /**
     * 更新会员等级
     *
     * @param user
     * @return
     */
    @Override
    public Result updateUserLevel(User user) {

        User originUser = userMapper.selectByPrimaryKey(user.getId());

        if (originUser.getIsBind() == User.NO_BIND_PHONE) {
            return ResultGenerator.genFailResult("该用户未绑定手机号");
        }
        int identity = originUser.getIdentity();

        user.setPhone(originUser.getPhone());
        if (originUser.getMemberLevel() > user.getMemberLevel()) {
            return ResultGenerator.genFailResult("高等级会员不能变成低等级会员");
        }

        UserBenefit queryUserBenefit = new UserBenefit();
        queryUserBenefit.setMemberLevel(user.getMemberLevel());
        UserBenefit originUserBenfit = userBenefitMapper.selectOne(queryUserBenefit);
        user.setUserBenefitId(originUserBenfit.getId());

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUpdateTime(new Date());
        updateUser.setExpirationTime(user.getExpirationTime());
        updateUser.setMemberLevel(user.getMemberLevel());
        updateUser.setIsExpiration(1);
        updateUser.setUserBenefitId(originUserBenfit.getId());
        int memberLevel = user.getMemberLevel();

        if (memberLevel == 2 || memberLevel == 3 || memberLevel == 4) {  //修改个人会员等级

            updateUser.setIdentity(User.USER_MENNBER_IDENTITY);

        } else {      //修改企业会员等级
            if (identity == User.USER_ANGET_IDENTITY || identity == User.USER_DEALER_IDENTITY) {
                return ResultGenerator.genFailResult("该用户是代理商或经销商身份,不能升级为企业身份");
            }

            updateUser.setIdentity(User.USER_COMPANY_IDENTITY);
            updateUser.setCompanyName(user.getCompanyName());
            updateUser.setName(user.getName());

            if (originUser.getMemberLevel() < 5) {   //普通会员转企业会员
                //生成一个后台管理员信息
                addAdministrator(originUser);
            }
        }
        userMapper.updateByPrimaryKeySelective(updateUser);
        //更新子用户信息
        updateUserSub(user);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 作假删除操作
     *
     * @param id
     */
    @Override
    public void updateUserDelete(Integer id) {

        User originUser = userMapper.selectByPrimaryKey(id);

        String phone = originUser.getPhone();

        if (originUser.getIdentity() == User.USER_COMPANY_IDENTITY) { //企业身份

            deleteCompany(originUser);

        } else if (originUser.getIdentity() == User.USER_ANGET_IDENTITY) { //代理商身份

            //删除代理商
            deleteAgent(phone);

        } else if (originUser.getIdentity() == User.USER_DEALER_IDENTITY) { //经销商身份

            //删除经销商
            deleteDealer(phone);
        }
        updateUserMember(originUser);
    }

    /**
     * 更新子用户信息
     *
     * @param user
     */
    private void updateUserSub(User user) {

        UserSub queryUserSub = new UserSub();
        queryUserSub.setPhone(user.getPhone());
        UserSub originUserSub = userSubMapper.selectOne(queryUserSub);

        UserSub updateUserSub = new UserSub();
        updateUserSub.setId(originUserSub.getId());
        updateUserSub.setMemberLevel(user.getMemberLevel());
        updateUserSub.setExpirationTime(user.getExpirationTime());

        updateUserSub.setBenfitId(user.getUserBenefitId());
        updateUserSub.setApplyName(user.getName());
        updateUserSub.setCompanyName(user.getCompanyName());
        updateUserSub.setUpdateTime(new Date());
        userSubMapper.updateByPrimaryKeySelective(updateUserSub);
    }

    /**
     * 添加一个企业后台管理员信息
     */
    private void addAdministrator(User user) {

        Administrator administrator = new Administrator();
        administrator.setUserId(user.getId());
        administrator.setRoleCode(Administrator.COMPANY_ROLE);
        Role role = new Role();
        role.setUniqueCode(Administrator.COMPANY_ROLE);
        role = roleMapper.selectOne(role);
        administrator.setRoleId(role.getId());
        administrator.setRoleName(role.getName());
        administrator.setHeadPortrait(LeanMarkentingConstant.headImg);
        administrator.setPhone(user.getPhone());
        administrator.setCreateTime(new Date());
        administrator.setUpdateTime(new Date());
        administrator.setUsername(user.getPhone());
        administrator.setPassword(MD5Util.MD5(user.getPhone()));
        administratorMapper.insertSelective(administrator);

    }


    /**
     * 删除代理商
     */
    public void deleteAgent(String phone) {
        Agent queryAgent = new Agent();
        queryAgent.setPhone(phone);
        Agent originAgent = agentMapper.selectOne(queryAgent);

        if (originAgent != null) {
            //删除代理商
            agentMapper.deleteByPrimaryKey(originAgent.getId());
            //删除代理商管理员账号
            Administrator queryAdministrator = new Administrator();
            queryAdministrator.setIdentityId(originAgent.getId());
            queryAdministrator.setRoleCode(Administrator.AGENT_ROLE);

            Administrator originAdministrator = administratorMapper.selectOne(queryAdministrator);
            if (originAdministrator != null) {
                administratorMapper.deleteByPrimaryKey(originAdministrator.getId());
            }
        }
    }

    /**
     * 删除经销商
     */
    public void deleteDealer(String phone) {
        Dealer queryDealer = new Dealer();
        queryDealer.setPhone(phone);
        Dealer originDealer = dealerMapper.selectOne(queryDealer);

        if (originDealer != null) {
            //删除经销商
            dealerMapper.deleteByPrimaryKey(originDealer.getId());
            //删除经销商管理员账号
            Administrator queryAdministrator = new Administrator();
            queryAdministrator.setIdentityId(originDealer.getId());
            queryAdministrator.setRoleCode(Administrator.Dealer_ROLE);

            Administrator originAdministrator = administratorMapper.selectOne(queryAdministrator);
            if (originAdministrator != null) {
                administratorMapper.deleteByPrimaryKey(originAdministrator.getId());
            }
        }
    }

    /**
     * 删除企业会员
     */
    public void deleteCompany(User user) {
        if (user.getMemberLevel() == 5 ||
                user.getMemberLevel() == 6 ||
                user.getMemberLevel() == 7) {//企业身份

            //删除企业管理员账号
            Administrator queryAdministrator = new Administrator();
            queryAdministrator.setUserId(user.getId());
            queryAdministrator.setRoleCode(Administrator.COMPANY_ROLE);
            Administrator originAdministrator = administratorMapper.selectOne(queryAdministrator);

            //删除管理员信息
            if (originAdministrator != null) {
                administratorMapper.deleteByPrimaryKey(originAdministrator.getId());
            }
            //删除企业员工信息
            Employee queryEmployee = new Employee();
            queryEmployee.setUserId(user.getId());
            List<Employee> employeeList = employeeMapper.select(queryEmployee);

            if (!CollectionUtils.isEmpty(employeeList)) {
                for (Employee employee : employeeList) {

                    User queryEmployeeUser = new User();
                    queryEmployeeUser.setPhone(employee.getPhone());
                    User employeeUser = userMapper.selectOne(queryEmployeeUser);
                    if (employeeUser != null) {
                        //修改用户信息
                        updateUserMember(employeeUser);
                    }
                    employeeMapper.deleteByPrimaryKey(employee.getId());

                }
            }
        }
    }

    /**
     * 变成普通会员并删除子用户
     */
//    public void updateUserMember(User user){
//
//        User updateUser = new User();
//        updateUser.setId(user.getId());
//        updateUser.setIsBind(User.NO_BIND_PHONE);
//        updateUser.setIdentity(User.USER_MENNBER_IDENTITY);
//        updateUser.setPhone("123456");
//        updateUser.setMemberLevel(-1);
//        updateUser.setName("");
//        updateUser.setUserBenefitId(-1);
//        updateUser.setCompanyName("");
//        updateUser.setExpirationTime(DateUtil.addTime(new Date(),100));
//        updateUser.setAgentProvince("");
//        updateUser.setSuperiorId(-1);
//        userMapper.updateByPrimaryKeySelective(updateUser);
//        //删除子用户
//        UserSub querySub = new UserSub();
//        querySub.setPhone(user.getPhone());
//        UserSub originSub = userSubMapper.selectOne(querySub);
//
//        if (originSub!=null){
//            userSubMapper.deleteByPrimaryKey(originSub.getId());
//        }
//    }
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
