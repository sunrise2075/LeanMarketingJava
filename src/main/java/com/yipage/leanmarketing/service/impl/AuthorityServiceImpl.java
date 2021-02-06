package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.AuthorityMapper;
import com.yipage.leanmarketing.model.Authority;
import com.yipage.leanmarketing.service.AuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/12/20.
 */
@Service
@Transactional
public class AuthorityServiceImpl extends AbstractService<Authority> implements AuthorityService {
    @Resource
    private AuthorityMapper authorityMapper;

}
