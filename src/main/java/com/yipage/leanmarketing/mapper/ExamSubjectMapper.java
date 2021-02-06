package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.ExamSubject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamSubjectMapper extends Mapper<ExamSubject> {

    List<ExamSubject> getRecommendExamSubject(@Param("subjectId") Integer subjectId, @Param("categoryId") Integer categoryId,
                                              @Param("limit") Integer limit);

    Long recommendedExamsCount(Integer userId);

    List<ExamSubject> recommendedExams(@Param("startIndex") Integer startIndex,
                                       @Param("limit") Integer limit,
                                       @Param("userId") Integer userId);

    List<ExamSubject> getByLabels(String[] array);

    Long getBySorceRecordCount();

    List<ExamSubject> getBySorceRecord(@Param("startIndex") Integer startIndex,
                                       @Param("limit") Integer limit);

    List<ExamSubject> getByLabel(String label);
}