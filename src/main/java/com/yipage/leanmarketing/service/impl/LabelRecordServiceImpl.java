package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.LabelRecordMapper;
import com.yipage.leanmarketing.model.LabelRecord;
import com.yipage.leanmarketing.service.LabelRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/06/10.
 */
@Service
@Transactional
public class LabelRecordServiceImpl extends AbstractService<LabelRecord> implements LabelRecordService {
    @Resource
    private LabelRecordMapper labelRecordMapper;

}
