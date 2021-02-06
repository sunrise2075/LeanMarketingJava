package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ExamSubject;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
public interface ExamSubjectService extends Service<ExamSubject> {

    List<ExamSubject> select(ExamSubject examSubject);

    List<ExamSubject> getRecommendExamSubject(Integer subjectId, Integer limit);

    Map<String, Object> recommendedExams(Integer page, Integer limit, Integer userId);

    Map getByLabels(String labels);

}
