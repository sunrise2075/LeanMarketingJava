package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.UserCollectionVideoMapper;
import com.yipage.leanmarketing.model.UserCollectionVideo;
import com.yipage.leanmarketing.service.UserCollectionVideoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class UserCollectionVideoServiceImpl extends AbstractService<UserCollectionVideo> implements UserCollectionVideoService {
    @Resource
    private UserCollectionVideoMapper userCollectionVideoMapper;

    @Override
    public List<UserCollectionVideo> select(UserCollectionVideo userCollectionVideo) {
        return userCollectionVideoMapper.select(userCollectionVideo);
    }
}
