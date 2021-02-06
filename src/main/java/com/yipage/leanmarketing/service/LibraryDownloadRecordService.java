package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.LibraryDownloadRecord;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/17.
 */
public interface LibraryDownloadRecordService extends Service<LibraryDownloadRecord> {

    List<LibraryDownloadRecord> select(LibraryDownloadRecord record);

    Integer selectCount(LibraryDownloadRecord libraryDownloadRecord);
}
