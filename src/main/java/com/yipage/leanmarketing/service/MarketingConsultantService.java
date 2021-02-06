package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.MarketingConsultant;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/16.
 */
public interface MarketingConsultantService extends Service<MarketingConsultant> {

    List<MarketingConsultant> select(MarketingConsultant marketingConsultant);
}
