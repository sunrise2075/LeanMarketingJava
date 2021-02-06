package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.LeanMarketingMapper;
import com.yipage.leanmarketing.model.LeanMarketing;
import com.yipage.leanmarketing.service.LeanMarketingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/16.
 */
@Service
@Transactional
public class LeanMarketingServiceImpl extends AbstractService<LeanMarketing> implements LeanMarketingService {
    @Resource
    private LeanMarketingMapper leanMarketingMapper;

    @Override
    public List<LeanMarketing> select(LeanMarketing leanMarketing) {
        return leanMarketingMapper.select(leanMarketing);
    }
}
