package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.MarketingConsultantMapper;
import com.yipage.leanmarketing.model.MarketingConsultant;
import com.yipage.leanmarketing.service.MarketingConsultantService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/16.
 */
@Service
@Transactional
public class MarketingConsultantServiceImpl extends AbstractService<MarketingConsultant> implements MarketingConsultantService {
    @Resource
    private MarketingConsultantMapper marketingConsultantMapper;

    @Override
    public List<MarketingConsultant> select(MarketingConsultant marketingConsultant) {
        return marketingConsultantMapper.select(marketingConsultant);
    }
}
