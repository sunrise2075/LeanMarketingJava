package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Banner;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/11.
 */
public interface BannerService extends Service<Banner> {

    List<Banner> select(Banner banner);
}
