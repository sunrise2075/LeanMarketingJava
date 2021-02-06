package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.constant.LeanMarkentingConstant;
import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.*;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.UserRechargeOrderService;
import com.yipage.leanmarketing.utils.MD5Util;
import com.yipage.leanmarketing.utils.Tools;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/18.
 */
@Service
@Transactional
public class UserRechargeOrderServiceImpl extends AbstractService<UserRechargeOrder> implements UserRechargeOrderService {
    @Resource
    private UserRechargeOrderMapper userRechargeOrderMapper;

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserSubMapper userSubMapper;
    @Resource
    private UserBenefitMapper userBenefitMapper;
    @Resource
    private ConsumeRecordMapper consumeRecordMapper;
    @Resource
    private AdministratorMapper administratorMapper;
    @Resource
    private RoleMapper roleMapper;


    @Override
    public Integer save(UserRechargeOrder model) {

        String out_trade_no = Tools.creatOrderNumber();
        User u = new User();
        u.setId(model.getUserId());
        u.setWxid(model.getOpenid());
        User user = userMapper.selectOne(u);
        if (user != null) {
            model.setUserId(user.getId());
            model.setUserName(user.getNickname());
        }
        model.setPayNumber(out_trade_no);
        model.setPayState(UserRechargeOrder.NOPAY);
        model.setPayment("微信支付");
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        Integer result = userRechargeOrderMapper.insert(model);
        if (result > 0) {
            return model.getId();
        }
        return null;
    }

    @Override
    public List<UserRechargeOrder> select(UserRechargeOrder userRechargeOrder) {
        return userRechargeOrderMapper.select(userRechargeOrder);
    }

    @Override
    public void afterPaySucess(UserRechargeOrder userRechargeOrder) {
        //更新订单
        UserRechargeOrder updateOrder = new UserRechargeOrder();
        updateOrder.setId(userRechargeOrder.getId());
        updateOrder.setPayTime(new Date());
        updateOrder.setPayState(UserRechargeOrder.ISPAY);
        userRechargeOrderMapper.updateByPrimaryKeySelective(updateOrder);

        //找到购买的权益信息
        UserBenefit userBenefit = userBenefitMapper.selectByPrimaryKey(userRechargeOrder.getBenfitId());
        //找到用户信息
        User user = userMapper.selectByPrimaryKey(userRechargeOrder.getUserId());
        //更新会员信息
        User u = new User();
        u.setId(user.getId());
        u.setPhone(userRechargeOrder.getPhone());
        //设置为没过期
        u.setIsExpiration(1);
        if (userBenefit != null) {
            if (userBenefit.getType() == 1) {  //个人会员
                if (userRechargeOrder.getYearType() == 1) {  //半年
                    calculateExpirationTime(2, 6, u);
                } else {  //一年
                    calculateExpirationTime(1, 1, u);
                }
                u.setIdentity(User.USER_MENNBER_IDENTITY);
            } else {  //企业会员
                calculateExpirationTime(1, 1, u);
                u.setIdentity(User.USER_COMPANY_IDENTITY);
                u.setName(userRechargeOrder.getName());
                u.setCompanyName(userRechargeOrder.getCompanyName());

                Administrator a = new Administrator();
                a.setUserId(userRechargeOrder.getUserId());
                Administrator b = administratorMapper.selectOne(a);
                if (b == null) {
                    //添加后台管理员信息
                    Administrator administrator = new Administrator();
                    administrator.setUserId(userRechargeOrder.getUserId());
                    administrator.setRoleCode(Administrator.COMPANY_ROLE);
                    Role role = new Role();
                    role.setUniqueCode(Administrator.COMPANY_ROLE);
                    role = roleMapper.selectOne(role);
                    administrator.setRoleId(role.getId());
                    administrator.setPhone(userRechargeOrder.getPhone());
                    administrator.setRoleName(role.getName());
                    administrator.setHeadPortrait(LeanMarkentingConstant.headImg);
                    administrator.setCreateTime(new Date());
                    administrator.setUpdateTime(new Date());
                    administrator.setUsername(userRechargeOrder.getPhone());
                    administrator.setPassword(MD5Util.MD5(userRechargeOrder.getPhone()));
                    administratorMapper.insertSelective(administrator);
                } else {
                    Administrator c = new Administrator();
                    c.setId(b.getId());
                    c.setUsername(userRechargeOrder.getPhone());
                    c.setPassword(MD5Util.MD5(userRechargeOrder.getPhone()));
                    administratorMapper.updateByPrimaryKeySelective(c);
                }
            }
            u.setUserBenefitId(userRechargeOrder.getBenfitId());
            u.setMemberLevel(userBenefit.getMemberLevel());
        }
        userMapper.updateByPrimaryKeySelective(u);

        //查是否已存在子用户
        UserSub userSub = new UserSub();
        userSub.setPhone(userRechargeOrder.getPhone());
        userSub.setOpenid(userRechargeOrder.getOpenid());

        List<UserSub> userSubList = userSubMapper.select(userSub);
        if (CollectionUtils.isEmpty(userSubList)) {
            //为空说明不存在就创建子用户
            userSub.setOpenid(user.getWxid());
            userSub.setMemberLevel(userBenefit.getMemberLevel());
            userSub.setExpirationTime(u.getExpirationTime());
            userSub.setBenfitId(userBenefit.getId());
            userSub.setIdentity(u.getIdentity());
            userSub.setApplyName(userRechargeOrder.getName());
            userSub.setCompanyName(userRechargeOrder.getCompanyName());
            userSub.setCreateTime(new Date());
            userSub.setUpdateTime(new Date());
            userSubMapper.insertSelective(userSub);
        } else {
            //存在就更新
            userSub.setId(userSubList.get(0).getId());
            userSub.setMemberLevel(userBenefit.getMemberLevel());
            userSub.setExpirationTime(u.getExpirationTime());
            userSub.setBenfitId(userBenefit.getId());
            userSub.setApplyName(userRechargeOrder.getName());
            userSub.setCompanyName(userRechargeOrder.getCompanyName());
            userSub.setUpdateTime(new Date());
            userSubMapper.updateByPrimaryKeySelective(userSub);
        }

        //生成消费记录
        ConsumeRecord consumeRecord = new ConsumeRecord();
        consumeRecord.setAddress(user.getRegisteredAddress());
        consumeRecord.setCreateTime(new Date());
        consumeRecord.setOrderNumber(userRechargeOrder.getPayNumber());
        consumeRecord.setPayMoney(userRechargeOrder.getPayMoney());
        consumeRecord.setPayTime(new Date());
        consumeRecord.setType(ConsumeRecord.CONSUME_TYPE_RECHARGE);
        consumeRecord.setUpdateTime(new Date());
        consumeRecord.setUserGrade(userBenefit.getMemberLevel());
        consumeRecord.setUserId(user.getId());
        consumeRecord.setUserName(user.getNickname());
        consumeRecord.setOpenid(user.getWxid());
        consumeRecord.setUserHeadPortrait(user.getHeadPortrait());

        consumeRecordMapper.insertSelective(consumeRecord);
    }

    private void calculateExpirationTime(int i, int i1, User user) {
        GregorianCalendar gc = new GregorianCalendar();
        if (user.getExpirationTime() == null) {
            //获取当前的时间戳
            gc.setTime(new Date());
            gc.add(i, i1);
            user.setExpirationTime(gc.getTime());
        } else {
            gc.setTime(user.getExpirationTime());
            gc.add(1, 1);
            user.setExpirationTime(gc.getTime());
        }
    }


}
