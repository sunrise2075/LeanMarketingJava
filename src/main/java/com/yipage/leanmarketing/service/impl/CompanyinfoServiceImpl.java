package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.CompanyinfoMapper;
import com.yipage.leanmarketing.model.Companyinfo;
import com.yipage.leanmarketing.service.CompanyinfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/03/16.
 */
@Service
@Transactional
public class CompanyinfoServiceImpl extends AbstractService<Companyinfo> implements CompanyinfoService {
    @Resource
    private CompanyinfoMapper companyinfoMapper;

}
