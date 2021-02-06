package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.AdministratorLogMapper;
import com.yipage.leanmarketing.model.AdministratorLog;
import com.yipage.leanmarketing.service.AdministratorLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class AdministratorLogServiceImpl extends AbstractService<AdministratorLog> implements AdministratorLogService {
    @Resource
    private AdministratorLogMapper administratorLogMapper;

}
