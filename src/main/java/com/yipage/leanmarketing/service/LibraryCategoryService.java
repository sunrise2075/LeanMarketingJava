package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.LibraryCategory;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/13.
 */
public interface LibraryCategoryService extends Service<LibraryCategory> {

    List<LibraryCategory> select(LibraryCategory libraryCategory);
}
