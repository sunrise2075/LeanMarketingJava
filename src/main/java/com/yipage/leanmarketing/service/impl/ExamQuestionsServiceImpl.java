package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ExamOptionMapper;
import com.yipage.leanmarketing.mapper.ExamQuestionsMapper;
import com.yipage.leanmarketing.mapper.ExamSubjectMapper;
import com.yipage.leanmarketing.model.ExamOption;
import com.yipage.leanmarketing.model.ExamQuestions;
import com.yipage.leanmarketing.model.ExamSubject;
import com.yipage.leanmarketing.service.ExamQuestionsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Service
@Transactional
public class ExamQuestionsServiceImpl extends AbstractService<ExamQuestions> implements ExamQuestionsService {
    @Resource
    private ExamQuestionsMapper examQuestionsMapper;

    @Resource
    private ExamSubjectMapper examSubjectMapper;

    @Resource
    private ExamOptionMapper examOptionMapper;

    @Override
    public Integer save(ExamQuestions examQuestions) {

        List<ExamOption> examOptions = examQuestions.getExamOptions();
        String options = "";
        if (!CollectionUtils.isEmpty(examOptions)) {

            options = options + "[";
            for (ExamOption examOption : examOptions) {

                if (StringUtils.isNotEmpty(examOption.getName())) {
                    options = options + '"' + examOption.getName() + '"' + ',';
                }
            }
            options = options.substring(0, options.length() - 1);
            options = options + "]";
        }
        examQuestions.setOptions(options);
        examQuestions.setCreateTime(new Date());
        examQuestions.setUpdateTime(new Date());
        ExamSubject examSubject = examSubjectMapper.selectByPrimaryKey(examQuestions.getSubjectId());
        if (examSubject != null) {
            examQuestions.setSubjectName(examSubject.getName());
        }

        int saveResult = examQuestionsMapper.insertSelective(examQuestions);

        if (saveResult > 0) {

            //保存试题选项
            saveExamOption(examOptions, examQuestions.getId());

        }

        return saveResult;
    }


    @Override
    public Integer update(ExamQuestions model) {

        //先删除试题选项
        ExamOption examOption = new ExamOption();
        examOption.setQuestionId(model.getId());
        List<ExamOption> examOptions = examOptionMapper.select(examOption);

        deleteExamOption(examOptions);

        List<ExamOption> examOptionList = model.getExamOptions();

        String options = "";
        if (!CollectionUtils.isEmpty(examOptionList)) {
            options = "[";
            for (ExamOption option : examOptionList) {

                if (StringUtils.isNotEmpty(option.getName())) {
                    options = options + '"' + option.getName() + '"' + ',';
                }
            }
            options = options.substring(0, options.length() - 1);
            options = options + "]";
        }
        model.setOptions(options);
        Integer updateResult = examQuestionsMapper.updateByPrimaryKeySelective(model);

        if (updateResult > 0) {
            //保存试题选项
            saveExamOption(examOptionList, model.getId());
        }
        return updateResult;
    }

    @Override
    public void deleteByIds(String ids) {

        if (ids != null && ids.length() > 0) {
            String[] id = ids.split(",");
            Integer[] deleteIds = new Integer[ids.length()];
            for (int i = 0; i < id.length; i++) {
                deleteIds[i] = Integer.parseInt(id[i]);
                //根据产品id查找对应的购买方式

                ExamOption examOption = new ExamOption();
                examOption.setQuestionId(deleteIds[i]);
                List<ExamOption> examOptions = examOptionMapper.select(examOption);
                //删除购买方式
                deleteExamOption(examOptions);
            }
            examQuestionsMapper.deleteByIds(ids);
        }
    }

    @Override
    public List<ExamQuestions> select(ExamQuestions examQuestions) {
        return examQuestionsMapper.select(examQuestions);
    }

    @Override
    public Integer selectCount(ExamQuestions examQuestions) {
        return examQuestionsMapper.selectCount(examQuestions);
    }


    /**
     * 保存试题选项
     *
     * @param examOptions
     */
    private void saveExamOption(List<ExamOption> examOptions, Integer questionId) {

        List<ExamOption> examOptionList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(examOptions)) {

            for (ExamOption examOption : examOptions) {

                if (StringUtils.isNotEmpty(examOption.getName())) {

                    examOption.setCreateTime(new Date());
                    examOption.setUpdateTime(new Date());
                    examOption.setQuestionId(questionId);
                    examOptionList.add(examOption);
                }
            }
        }
        examOptionMapper.insertList(examOptionList);
    }

    /**
     * 删除试题选项
     *
     * @param examOptions
     */
    private void deleteExamOption(List<ExamOption> examOptions) {

        if (!CollectionUtils.isEmpty(examOptions)) {
            String ids = "";
            for (ExamOption option : examOptions) {
                ids = ids + option.getId() + ",";
            }

            ids = ids.substring(0, ids.length() - 1);
            examOptionMapper.deleteByIds(ids);
        }
    }


}
