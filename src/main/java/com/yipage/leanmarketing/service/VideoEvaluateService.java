package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.VideoEvaluate;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
public interface VideoEvaluateService extends Service<VideoEvaluate> {

    List<VideoEvaluate> select(VideoEvaluate videoEvaluate);

    List<Map<String, Object>> selectOrderByCreateTime(VideoEvaluate videoEvaluate);

    Integer selectCount(VideoEvaluate videoEvaluate);
}
