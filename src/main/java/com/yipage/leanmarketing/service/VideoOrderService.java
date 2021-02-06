package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.VideoOrder;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
public interface VideoOrderService extends Service<VideoOrder> {

    List<VideoOrder> select(VideoOrder videoOrder);

    void afterPaySucess(VideoOrder videoOrder);

    Integer selectCount(VideoOrder videoOrder);
}
