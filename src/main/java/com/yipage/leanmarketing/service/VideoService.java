package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Video;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
public interface VideoService extends Service<Video> {

    List<Video> select(Video video);

    /**
     * 按销售量前十的视频
     */
    List<Video> orderBySalesNum();

    List<Video> findVideo(Integer categoryId, Integer isFree, Integer isShow, Integer limit);

    Map<String, Object> recommendedVideos(Integer page, Integer limit, Integer userId, Integer isFree);

    Map getByLabels(String labels);
}
