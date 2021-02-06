package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.VideoContentsMapper;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.VideoContents;
import com.yipage.leanmarketing.service.VideoContentsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Service
@Transactional
public class VideoContentsServiceImpl extends AbstractService<VideoContents> implements VideoContentsService {
    @Resource
    private VideoContentsMapper videoContentsMapper;

    /**
     * 按条件查找视频目录
     *
     * @param videoContents
     * @return
     */
    @Override
    public List<VideoContents> select(VideoContents videoContents) {
        return videoContentsMapper.select(videoContents);
    }

    @Override
    public Map<String, Object> listPagerToVideoContents(Integer page, Integer limit, String videoChapterName, String videoName) {
        Map<String, Object> map = new HashMap<>();

        Integer count = videoContentsMapper.countToVideoContents(videoChapterName, videoName);
        map.put("count", count);
        Integer beginIndex = (page - 1) * limit;
        List<ConsumeRecord> list = videoContentsMapper.listPagerToVideoContents(beginIndex, limit, videoChapterName, videoName);
        map.put("data", list);
        map.put("status", 1);
        return map;
    }
}
