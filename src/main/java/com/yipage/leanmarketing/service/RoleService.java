package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Role;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface RoleService extends Service<Role> {

    List<Role> select(Role role);
}
