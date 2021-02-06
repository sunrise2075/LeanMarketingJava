package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.VideoContents;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
public interface VideoContentsService extends Service<VideoContents> {

    List<VideoContents> select(VideoContents videoContents);

    Map<String, Object> listPagerToVideoContents(Integer page, Integer limit, String videoChapterName, String videoName);
}
