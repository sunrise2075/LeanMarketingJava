package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.LibraryCategoryMapper;
import com.yipage.leanmarketing.model.LibraryCategory;
import com.yipage.leanmarketing.service.LibraryCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/13.
 */
@Service
@Transactional
public class LibraryCategoryServiceImpl extends AbstractService<LibraryCategory> implements LibraryCategoryService {
    @Resource
    private LibraryCategoryMapper libraryCategoryMapper;

    @Override
    public List<LibraryCategory> select(LibraryCategory libraryCategory) {
        return libraryCategoryMapper.select(libraryCategory);
    }
}
