package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.LessonCollageRecord;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/04/15.
 */
public interface LessonCollageRecordService extends Service<LessonCollageRecord> {

    Integer selectCount(LessonCollageRecord queryRecord);

    List<LessonCollageRecord> select(LessonCollageRecord queryRecord);

    /**
     * 定时任务处理拼团失败
     */
    void OverdueTimeLessonCollage();

    void falseDelete(String ids);

    void updateAndSendMessage(LessonCollageRecord lessonCollageRecord);
}
