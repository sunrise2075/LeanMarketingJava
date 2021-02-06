package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.VideoCategoryMapper;
import com.yipage.leanmarketing.model.VideoCategory;
import com.yipage.leanmarketing.service.VideoCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class VideoCategoryServiceImpl extends AbstractService<VideoCategory> implements VideoCategoryService {
    @Resource
    private VideoCategoryMapper videoCategoryMapper;

    @Override
    public List<VideoCategory> select(VideoCategory videoCategory) {
        return videoCategoryMapper.select(videoCategory);
    }
}
