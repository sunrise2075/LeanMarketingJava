package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ExamSubjectMapper;
import com.yipage.leanmarketing.model.ExamSubject;
import com.yipage.leanmarketing.service.ExamSubjectService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Service
@Transactional
public class ExamSubjectServiceImpl extends AbstractService<ExamSubject> implements ExamSubjectService {
    @Resource
    private ExamSubjectMapper examSubjectMapper;

    @Override
    public List<ExamSubject> select(ExamSubject examSubject) {

        List<ExamSubject> list = examSubjectMapper.select(examSubject);
        //calculateQuestionNum(list);
        return list;
    }

    @Override
    public List<ExamSubject> getRecommendExamSubject(Integer subjectId, Integer limit) {

        List<ExamSubject> list = null;
        ExamSubject examSubject = null;
        //获取这个试题的试题分类
        if (subjectId == null) {
            list = examSubjectMapper.getRecommendExamSubject(null, null, limit);
        } else {
            examSubject = examSubjectMapper.selectByPrimaryKey(subjectId);
            list = examSubjectMapper.getRecommendExamSubject(examSubject.getId(), examSubject.getCategoryId(), limit);
        }
        //calculateQuestionNum(list);
        return list;
    }

    //    @Override
//    public Map<String, Object> recommendedExams(Integer page, Integer limit, Integer userId) {
//
//        Map<String, Object> map = new HashMap<>(4);
//        int startIndex = (page-1)*limit;
//        List<ExamSubject> examSubjectList = new ArrayList<>();
//        //查询数量
//        Long count = examSubjectMapper.recommendedExamsCount(userId);
//        if (count>0){
//            examSubjectList = examSubjectMapper.recommendedExams(startIndex,limit,userId);
//        }
//        map.put("count",count);
//        map.put("data",examSubjectList);
//        map.put("code",200);
//        map.put("status",1);
//        return map;
//    }
    @Override
    public Map<String, Object> recommendedExams(Integer page, Integer limit, Integer userId) {

        Map<String, Object> map = new HashMap<>(4);
        int startIndex = (page - 1) * limit;
        List<ExamSubject> examSubjectList = null;
        List<ExamSubject> examSubjectList2 = null;
        //查询推荐数量
        Long count = examSubjectMapper.recommendedExamsCount(userId);
        if (count <= 0) {

            //按照考试记录进行排序
            count = examSubjectMapper.getBySorceRecordCount();
            examSubjectList = examSubjectMapper.getBySorceRecord(startIndex, limit);

        } else {
            examSubjectList = examSubjectMapper.recommendedExams(startIndex, limit, userId);
            if (count > 0 && count < 10) {
                Long count2;
                count2 = examSubjectMapper.getBySorceRecordCount();

                if (page == 1) {

                    //第一页
                    if (limit > count.intValue()) {

                        limit = limit - count.intValue();
                        examSubjectList2 = examSubjectMapper.getBySorceRecord(startIndex, limit);
                        examSubjectList.addAll(examSubjectList2);
                        //去除重复元素
                        examSubjectList = removerList(examSubjectList);
                    }
                } else {
                    //其他页
                    startIndex = (page - 1) * limit + count.intValue();
                    examSubjectList = examSubjectMapper.getBySorceRecord(startIndex, limit);
                }
                //总条数
                count = count + count2;
            }
        }
        map.put("count", count);
        map.put("data", examSubjectList);
        map.put("code", 200);
        map.put("status", 1);
        return map;
    }

    @Override
    public Map getByLabels(String labels) {
        Map map = null;
        String[] array = labels.split(",");

        List<Integer> listAll = new ArrayList<>();

        if (array.length > 0) {
            map = new HashMap(array.length);
            for (int i = 0; i < array.length; i++) {

                List<ExamSubject> list = examSubjectMapper.getByLabel(array[i]);

                if (!CollectionUtils.isEmpty(list)) {

                    Iterator<ExamSubject> exams = list.iterator();
                    while (exams.hasNext()) {

                        ExamSubject examSubject = exams.next();

                        if (listAll.contains(examSubject.getId())) {
                            exams.remove();
                        } else {
                            listAll.add(examSubject.getId());
                        }
                    }

                }
                //还需要去重
                map.put("list" + i, list);
            }
        }
        return map;
    }


    /**
     * 计算试题里面有多少题目
     */
//    private void calculateQuestionNum(List<ExamSubject> list) {
//
//        if(!CollectionUtils.isEmpty(list)){
//            for (ExamSubject subject : list) {
//                //算出这个试题下面有多少题
//                ExamQuestions examQuestions = new ExamQuestions();
//                examQuestions.setSubjectId(subject.getId());
//                Integer questionNum = examQuestionsMapper.selectCount(examQuestions);
//                subject.setQuestionNum(questionNum);
//            }
//        }
//    }
    //除去重复元素的方法:
    public List removerList(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator it = list.iterator(); it.hasNext(); ) {
            Object element = it.next();
            if (set.add(element)) {
                newList.add(element);
            }
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

}
