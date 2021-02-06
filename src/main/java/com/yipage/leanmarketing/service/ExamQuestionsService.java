package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ExamQuestions;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
public interface ExamQuestionsService extends Service<ExamQuestions> {

    List<ExamQuestions> select(ExamQuestions examQuestions);

    Integer selectCount(ExamQuestions examQuestions);
}
