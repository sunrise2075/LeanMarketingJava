package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ExamCategoryMapper;
import com.yipage.leanmarketing.model.ExamCategory;
import com.yipage.leanmarketing.service.ExamCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Service
@Transactional
public class ExamCategoryServiceImpl extends AbstractService<ExamCategory> implements ExamCategoryService {
    @Resource
    private ExamCategoryMapper examCategoryMapper;

    @Override
    public List<ExamCategory> select(ExamCategory examCategory) {
        return examCategoryMapper.select(examCategory);
    }


}
