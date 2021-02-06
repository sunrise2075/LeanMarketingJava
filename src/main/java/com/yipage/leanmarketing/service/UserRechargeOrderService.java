package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.UserRechargeOrder;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/18.
 */
public interface UserRechargeOrderService extends Service<UserRechargeOrder> {

    List<UserRechargeOrder> select(UserRechargeOrder userRechargeOrder);

    void afterPaySucess(UserRechargeOrder userRechargeOrder);
}
