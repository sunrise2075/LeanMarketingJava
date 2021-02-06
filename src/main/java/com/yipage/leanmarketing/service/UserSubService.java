package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.UserSub;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/01/08.
 */
public interface UserSubService extends Service<UserSub> {

    List<UserSub> select(UserSub userSub);
}
