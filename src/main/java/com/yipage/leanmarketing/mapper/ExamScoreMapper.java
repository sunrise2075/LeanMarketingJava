package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.ExamScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamScoreMapper extends Mapper<ExamScore> {

    Integer getAverageByDay(@Param("day") String day, @Param("openid") String openid);

    Integer getCount(@Param("day") String day, @Param("openid") String openid);

    List<ExamScore> getByDay(@Param("beginIndex") Integer beginIndex, @Param("limit") Integer limit,
                             @Param("day") String day, @Param("openid") String openid);

    Integer getHighestScore(@Param("time1") String time1, @Param("time2") String time2, @Param("openid") String openid);


}