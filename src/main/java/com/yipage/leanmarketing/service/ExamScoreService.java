package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ExamScore;

import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
public interface ExamScoreService extends Service<ExamScore> {

    Integer getAverageByDay(String day, String openid);

    Map<String, Object> getByDay(Integer page, Integer limit, String day, String openid);

    Integer getHighestScore(String time1, String time2, String openid);

}
