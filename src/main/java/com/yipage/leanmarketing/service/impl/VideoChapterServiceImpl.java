package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.VideoChapterMapper;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.VideoChapter;
import com.yipage.leanmarketing.service.VideoChapterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/26.
 */
@Service
@Transactional
public class VideoChapterServiceImpl extends AbstractService<VideoChapter> implements VideoChapterService {
    @Resource
    private VideoChapterMapper videoChapterMapper;

    @Override
    public Map<String, Object> listPagerToVideoChapter(Integer page, Integer limit, String videoChapterName, String videoName, Integer videoId) {
        Map<String, Object> map = new HashMap<>();

        Integer count = videoChapterMapper.countToVideoChapter(videoChapterName, videoName, videoId);
        map.put("count", count);
        Integer beginIndex = (page - 1) * limit;
        List<ConsumeRecord> list = videoChapterMapper.listPagerToVideoChapter(beginIndex, limit, videoChapterName, videoName, videoId);
        map.put("data", list);
        map.put("status", 1);
        return map;
    }

    @Override
    public List<VideoChapter> select(VideoChapter chapter) {
        return videoChapterMapper.select(chapter);
    }
}
