package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.LabelMapper;
import com.yipage.leanmarketing.model.Label;
import com.yipage.leanmarketing.service.LabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/06/10.
 */
@Service
@Transactional
public class LabelServiceImpl extends AbstractService<Label> implements LabelService {
    @Resource
    private LabelMapper labelMapper;

    @Override
    public List<Label> select(Label label) {
        return labelMapper.select(label);
    }
}
