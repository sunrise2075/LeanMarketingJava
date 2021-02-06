package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ExamAbilityMapper;
import com.yipage.leanmarketing.mapper.ExamSubjectMapper;
import com.yipage.leanmarketing.model.ExamAbility;
import com.yipage.leanmarketing.model.ExamSubject;
import com.yipage.leanmarketing.service.ExamAbilityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/06/28.
 */
@Service
@Transactional
public class ExamAbilityServiceImpl extends AbstractService<ExamAbility> implements ExamAbilityService {
    @Resource
    private ExamAbilityMapper examAbilityMapper;

    @Resource
    private ExamSubjectMapper examSubjectMapper;

    @Override
    public List<ExamAbility> select(ExamAbility examAbility) {
        return examAbilityMapper.select(examAbility);
    }

    @Override
    public List<ExamAbility> getAbilityListById(Integer subjectId) {

        ExamSubject examSubject = examSubjectMapper.selectByPrimaryKey(subjectId);
        if (examSubject != null) {
            String abilitys = examSubject.getAbilitys();
            if (StringUtils.isNotEmpty(abilitys)) {
                String[] split = abilitys.split(",");
                //转成整型数组
                int[] array = new int[split.length];
                for (int i = 0, len = split.length; i < len; i++) {
                    array[i] = Integer.parseInt(split[i]);
                    System.out.println(array[i]);
                }

                List<ExamAbility> list = examAbilityMapper.getAbilityListByAbilitys(array);
                return list;
            }
        }
        return null;
    }
}
