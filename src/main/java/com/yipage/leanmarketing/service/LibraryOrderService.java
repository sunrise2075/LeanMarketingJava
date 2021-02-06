package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.LibraryOrder;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/14.
 */
public interface LibraryOrderService extends Service<LibraryOrder> {

    List<LibraryOrder> select(LibraryOrder libraryOrder);

    void afterPaySucess(LibraryOrder libraryOrder);
}
