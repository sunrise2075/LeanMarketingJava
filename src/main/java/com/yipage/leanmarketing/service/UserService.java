package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.User;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface UserService extends Service<User> {
    /**
     * 微信授权
     *
     * @param code
     * @param headimgurl
     * @param nickname
     * @param province
     * @param city       @return
     */
    User weixinAuthority(String code, String headimgurl, String nickname, String province, String city, Integer superiorId);

    List<User> exportExcel(String identity, String memberLevel, String address, String superiorId);

    List<User> select(User user);

    Result updateUserLevel(User user);

    void updateUserDelete(Integer id);
}
