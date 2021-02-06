package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.VideoChapter;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/26.
 */
public interface VideoChapterService extends Service<VideoChapter> {

    Map<String, Object> listPagerToVideoChapter(Integer page, Integer limit, String videoChapterName, String videoName, Integer videoId);

    List<VideoChapter> select(VideoChapter chapter);
}
