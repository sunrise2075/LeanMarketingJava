package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.UserCollectionVideo;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface UserCollectionVideoService extends Service<UserCollectionVideo> {

    List<UserCollectionVideo> select(UserCollectionVideo userCollectionVideo);
}
