package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.BlackMapper;
import com.yipage.leanmarketing.model.Black;
import com.yipage.leanmarketing.service.BlackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/01/17.
 */
@Service
@Transactional
public class BlackServiceImpl extends AbstractService<Black> implements BlackService {
    @Resource
    private BlackMapper blackMapper;

}
