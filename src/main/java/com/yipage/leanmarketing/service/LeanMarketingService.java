package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.LeanMarketing;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/16.
 */
public interface LeanMarketingService extends Service<LeanMarketing> {

    List<LeanMarketing> select(LeanMarketing leanMarketing);
}
