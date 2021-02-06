package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Library;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/13.
 */
public interface LibraryService extends Service<Library> {

    List<Library> select(Library library);

    List<Library> findByKeyWord(String title, String fileType, Integer categoryId);

    Map<String, Object> recommendedLibrarys(Integer page, Integer limit, Integer userId, String fileType, Integer isFree);

    Map getByLabels(String labels);
}
