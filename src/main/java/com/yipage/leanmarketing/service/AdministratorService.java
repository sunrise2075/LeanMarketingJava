package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Administrator;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface AdministratorService extends Service<Administrator> {
    /**
     * 通过用户名和密码查找用户
     *
     * @param username
     * @param password
     * @return
     */
    Administrator getAdministratorByUsernameAndPassword(String username, String password);

    List<Administrator> select(Administrator conditionAdministrator);

    Integer update2(Administrator administrator);

}
