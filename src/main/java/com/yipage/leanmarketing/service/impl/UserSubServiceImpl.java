package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.UserSubMapper;
import com.yipage.leanmarketing.model.UserSub;
import com.yipage.leanmarketing.service.UserSubService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/01/08.
 */
@Service
@Transactional
public class UserSubServiceImpl extends AbstractService<UserSub> implements UserSubService {
    @Resource
    private UserSubMapper userSubMapper;

    @Override
    public List<UserSub> select(UserSub userSub) {
        return userSubMapper.select(userSub);
    }
}
