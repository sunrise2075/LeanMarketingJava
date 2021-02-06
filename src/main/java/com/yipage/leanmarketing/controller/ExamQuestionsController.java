package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.*;
import com.yipage.leanmarketing.service.*;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/12.
 */
@RestController
@RequestMapping("/exam/questions")
public class ExamQuestionsController {

    @Resource
    private ExamQuestionsService examQuestionsService;
    @Resource
    private ExamSubjectService examSubjectService;
    @Resource
    private ExamCategoryService examCategoryService;

    @Resource
    private ExamOptionService examOptionService;

    @Resource
    private ExamAbilityService examAbilityService;

    @PostMapping("add")
    public Result add(ExamQuestions examQuestions) {

        examQuestionsService.save(examQuestions);

        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        examQuestionsService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(ExamQuestions examQuestions) {
        examQuestions.setUpdateTime(new Date());
        examQuestionsService.update(examQuestions);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Map<String, Object> resMap = new HashMap<>();
        ExamQuestions examQuestions = examQuestionsService.findById(id);

        if (examQuestions != null) {

            ExamOption examOption = new ExamOption();
            examOption.setQuestionId(examQuestions.getId());
            List<ExamOption> examOptionList = examOptionService.select(examOption);
            examQuestions.setExamOptions(examOptionList);

            resMap.put("examQuestions", examQuestions);

            ExamSubject examSubject = examSubjectService.findById(examQuestions.getSubjectId());

            resMap.put("examSubject", examSubject);

            if (examSubject != null) {
                ExamCategory examCategory = examCategoryService.findById(examSubject.getCategoryId());
                List<ExamAbility> examAbilityList = examAbilityService.getAbilityListById(examQuestions.getSubjectId());
                resMap.put("examAbilityList", examAbilityList);
                resMap.put("examCategory", examCategory);
            }
        }
        return ResultGenerator.genSuccessResult(resMap);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "examName", required = false) String examName,
                    @RequestParam(value = "subjectId", required = false) String subjectId) {
        PageHelper.startPage(page, limit, "create_time desc");
        Condition condition = new Condition(ExamQuestions.class);
        Example.Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotEmpty(examName)) {
            criteria.andCondition("subject_name like  '%" + examName + "%'");
        }
        if (subjectId != null) {
            criteria.andCondition("subject_id =" + subjectId);
        }
        List<ExamQuestions> list = examQuestionsService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 根据条件查看试题题目
     *
     * @return
     */
    @GetMapping("select")
    public Result select(@RequestParam(value = "subjectId") Integer subjectId) {
        ExamQuestions examQuestions = new ExamQuestions();
        if (subjectId != null) {
            examQuestions.setSubjectId(subjectId);
        }
        List<ExamQuestions> list = examQuestionsService.select(examQuestions);
        if (!CollectionUtils.isEmpty(list)) {
            for (ExamQuestions questions : list) {
                ExamOption examOption = new ExamOption();
                examOption.setQuestionId(questions.getId());
                List<ExamOption> examOptions = examOptionService.select(examOption);
                questions.setExamOptions(examOptions);
            }
        }

        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 查询某个试题剩余题目的编码
     */
    @GetMapping("getExamCode")
    public Result getExamCode(@RequestParam(value = "subjectId") Integer subjectId,
                              @RequestParam(value = "code", required = false) Integer code) {
        Map<String, Object> map = new HashMap<>(2);
        ExamSubject examSubject = examSubjectService.findById(subjectId);
        ExamQuestions examQuestions = new ExamQuestions();
        if (subjectId != null) {
            examQuestions.setSubjectId(subjectId);
        }
        Integer size = 0;
        List<ExamQuestions> list = examQuestionsService.select(examQuestions);
        //已经存在的试题编码集合
        Integer[] codes = null;
        if (!CollectionUtils.isEmpty(list)) {
            size += list.size();
            codes = new Integer[size];
            for (int i = 0; i < list.size(); i++) {
                codes[i] = list.get(i).getCode();
            }
        }
        String codes3 = "";
        if (code != null) {
            size++;
            codes3 = codes3 + code + ",";
        }
        //Integer [] codes2 = new Integer [size];
        if (examSubject != null) {
            for (int i = 1; i <= examSubject.getQuestionNum(); i++) {

                if (codes != null && Arrays.asList(codes).contains(i)) {
                    continue;
                } else {
                    codes3 = codes3 + i + ",";
                }
            }
        }
        String codes6 = "";
        if (codes3.length() > 0) {
            codes3 = codes3.substring(0, codes3.length() - 1);
            String[] codes4 = codes3.split(",");
            Integer[] codes5 = new Integer[codes4.length];
            for (int i = 0; i < codes4.length; i++) {
                codes5[i] = Integer.parseInt(codes4[i]);
            }
            //排序
            Arrays.sort(codes5);
            for (int i = 0; i < codes5.length; i++) {

                codes6 = codes6 + codes5[i] + ",";
            }
        }
        if (codes6.length() > 0) {
            codes6 = codes6.substring(0, codes6.length() - 1);
        }
        map.put("code", codes6);

        //获取试题的能力标签
        map.put("examAbilityList", examAbilityService.getAbilityListById(subjectId));

        return ResultGenerator.genSuccessResult(map);
    }

}
