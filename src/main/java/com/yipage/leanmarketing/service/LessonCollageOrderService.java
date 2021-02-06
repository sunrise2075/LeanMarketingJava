package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.LessonCollageOrder;


/**
 * Created by CodeGenerator on 2019/04/16.
 */
public interface LessonCollageOrderService extends Service<LessonCollageOrder> {

    void afterPaySucess(LessonCollageOrder order);

    Result createOrder(LessonCollageOrder lessonCollageOrder);
}
