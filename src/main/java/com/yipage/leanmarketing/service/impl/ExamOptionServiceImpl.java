package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ExamOptionMapper;
import com.yipage.leanmarketing.model.ExamOption;
import com.yipage.leanmarketing.service.ExamOptionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/05/16.
 */
@Service
@Transactional
public class ExamOptionServiceImpl extends AbstractService<ExamOption> implements ExamOptionService {
    @Resource
    private ExamOptionMapper examOptionMapper;

    @Override
    public List<ExamOption> select(ExamOption examOption) {
        return examOptionMapper.select(examOption);
    }
}
