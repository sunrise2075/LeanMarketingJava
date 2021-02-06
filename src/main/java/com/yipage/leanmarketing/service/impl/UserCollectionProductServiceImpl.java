package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.UserCollectionProductMapper;
import com.yipage.leanmarketing.model.UserCollectionProduct;
import com.yipage.leanmarketing.service.UserCollectionProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class UserCollectionProductServiceImpl extends AbstractService<UserCollectionProduct> implements UserCollectionProductService {

    @Resource
    private UserCollectionProductMapper userCollectionProductMapper;

    @Override
    public List<UserCollectionProduct> select(UserCollectionProduct userCollectionProduct) {
        return userCollectionProductMapper.select(userCollectionProduct);
    }
}
