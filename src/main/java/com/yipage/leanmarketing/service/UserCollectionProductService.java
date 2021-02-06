package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.UserCollectionProduct;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface UserCollectionProductService extends Service<UserCollectionProduct> {

    List<UserCollectionProduct> select(UserCollectionProduct userCollectionProduct);
}
