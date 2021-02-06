package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.VideoEvaluateMapper;
import com.yipage.leanmarketing.model.VideoEvaluate;
import com.yipage.leanmarketing.service.VideoEvaluateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Service
@Transactional
public class VideoEvaluateServiceImpl extends AbstractService<VideoEvaluate> implements VideoEvaluateService {
    @Resource
    private VideoEvaluateMapper videoEvaluateMapper;

    @Override
    public List<VideoEvaluate> select(VideoEvaluate videoEvaluate) {
        return videoEvaluateMapper.select(videoEvaluate);
    }

    @Override
    public List<Map<String, Object>> selectOrderByCreateTime(VideoEvaluate videoEvaluate) {
        return videoEvaluateMapper.selectOrderByCreateTime(videoEvaluate);
    }

    @Override
    public Integer selectCount(VideoEvaluate videoEvaluate) {
        return videoEvaluateMapper.selectCount(videoEvaluate);
    }
}
