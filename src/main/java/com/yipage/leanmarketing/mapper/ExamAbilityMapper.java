package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.ExamAbility;

import java.util.List;

public interface ExamAbilityMapper extends Mapper<ExamAbility> {

    List<ExamAbility> getAbilityListByAbilitys(int[] array);
}