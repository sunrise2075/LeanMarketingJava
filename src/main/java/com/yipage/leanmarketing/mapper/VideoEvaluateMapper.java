package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.VideoEvaluate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VideoEvaluateMapper extends Mapper<VideoEvaluate> {

    List<Map<String, Object>> selectOrderByCreateTime(VideoEvaluate videoEvaluate);
}