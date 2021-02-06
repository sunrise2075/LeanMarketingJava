package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.VideoCategory;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface VideoCategoryService extends Service<VideoCategory> {

    List<VideoCategory> select(VideoCategory videoCategory);

}
