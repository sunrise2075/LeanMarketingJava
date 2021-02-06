package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.LessonCollageMapper;
import com.yipage.leanmarketing.model.LessonCollage;
import com.yipage.leanmarketing.service.LessonCollageService;
import com.yipage.leanmarketing.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


/**
 * Created by CodeGenerator on 2019/04/15.
 */
@Service
@Transactional
public class LessonCollageServiceImpl extends AbstractService<LessonCollage> implements LessonCollageService {
    @Resource
    private LessonCollageMapper lessonCollageMapper;

    @Override
    public Integer save(LessonCollage model) {
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setEndTime(DateUtil.addTime(model.getCreateTime(), model.getDays()));
        return super.save(model);
    }
}
