package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.LessonCollageRuleMapper;
import com.yipage.leanmarketing.model.LessonCollageRule;
import com.yipage.leanmarketing.service.LessonCollageRuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2019/04/15.
 */
@Service
@Transactional
public class LessonCollageRuleServiceImpl extends AbstractService<LessonCollageRule> implements LessonCollageRuleService {
    @Resource
    private LessonCollageRuleMapper lessonCollageRuleMapper;

}
