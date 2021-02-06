package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.WatchRecord;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/17.
 */
public interface WatchRecordService extends Service<WatchRecord> {

    List<WatchRecord> select(WatchRecord watchRecord);

    Integer selectCount(WatchRecord watchRecord);
}
