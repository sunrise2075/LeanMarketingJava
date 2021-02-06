package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.UserAddress;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/14.
 */
public interface UserAddressService extends Service<UserAddress> {

    List<UserAddress> select(UserAddress userAddress);

    Integer modifyDefaultAddress(Integer id, String openid);
}
