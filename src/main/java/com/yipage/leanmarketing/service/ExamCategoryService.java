package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ExamCategory;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
public interface ExamCategoryService extends Service<ExamCategory> {

    List<ExamCategory> select(ExamCategory examCategory);
}
