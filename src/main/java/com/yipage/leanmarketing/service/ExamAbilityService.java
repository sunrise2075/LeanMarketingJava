package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ExamAbility;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/06/28.
 */
public interface ExamAbilityService extends Service<ExamAbility> {

    List<ExamAbility> select(ExamAbility examAbility);

    List<ExamAbility> getAbilityListById(Integer subjectId);
}
