package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ExamOption;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/05/16.
 */
public interface ExamOptionService extends Service<ExamOption> {

    List<ExamOption> select(ExamOption examOption);
}
