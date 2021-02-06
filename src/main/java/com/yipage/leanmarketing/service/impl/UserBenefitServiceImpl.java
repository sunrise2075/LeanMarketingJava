package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.UserBenefitMapper;
import com.yipage.leanmarketing.model.UserBenefit;
import com.yipage.leanmarketing.service.UserBenefitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/12/18.
 */
@Service
@Transactional
public class UserBenefitServiceImpl extends AbstractService<UserBenefit> implements UserBenefitService {
    @Resource
    private UserBenefitMapper userBenefitMapper;

}
