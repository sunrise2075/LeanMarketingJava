package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.core.ServiceException;
import com.yipage.leanmarketing.mapper.ExamScoreMapper;
import com.yipage.leanmarketing.mapper.ExamSubjectMapper;
import com.yipage.leanmarketing.mapper.LabelRecordMapper;
import com.yipage.leanmarketing.model.ExamScore;
import com.yipage.leanmarketing.model.ExamSubject;
import com.yipage.leanmarketing.model.LabelRecord;
import com.yipage.leanmarketing.service.ExamScoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Service
@Transactional
public class ExamScoreServiceImpl extends AbstractService<ExamScore> implements ExamScoreService {
    @Resource
    private ExamScoreMapper examScoreMapper;

    @Resource
    private ExamSubjectMapper examSubjectMapper;

    @Resource
    private LabelRecordMapper labelRecordMapper;

    @Override
    public Integer save(ExamScore model) {

        //添加一个用户标签记录
        addOrUpdateLabelRecord(model);

        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        if (model.getScore() >= 85) {
            model.setEvaluate(ExamScore.EVALUATE_EXCELLENT);
        } else if (model.getScore() <= 84 && model.getScore() >= 75) {
            model.setEvaluate(ExamScore.EVALUATE_GOOD);
        } else if (model.getScore() <= 75 && model.getScore() >= 65) {
            model.setEvaluate(ExamScore.EVALUATE_COMMON);
        } else if (model.getScore() <= 65 && model.getScore() >= 60) {
            model.setEvaluate(ExamScore.EVALUATE_PASS);
        } else {
            model.setEvaluate(ExamScore.EVALUATE_NOPASS);
        }
        examScoreMapper.insert(model);
        return model.getId();
    }


    @Override
    public Integer getAverageByDay(String day, String openid) {
        return examScoreMapper.getAverageByDay(day, openid);
    }

    @Override
    public Map<String, Object> getByDay(Integer page, Integer limit, String day, String openid) {

        Map<String, Object> resMap = new HashMap<>();
        //Integer count = examScoreMapper.getCount(day,openid);
        //resMap.put("count",count);
        int beginIndex = (page - 1) * limit;
        List<ExamScore> list = examScoreMapper.getByDay(beginIndex, limit, day, openid);
        resMap.put("data", list);
        resMap.put("status", 1);
        return resMap;
    }

    /**
     * 获取前7天的最高分
     *
     * @param time1
     * @param time2
     * @return
     */
    @Override
    public Integer getHighestScore(String time1, String time2, String openid) {
        return examScoreMapper.getHighestScore(time1, time2, openid);
    }


    /**
     * 添加一个用户标签记录
     *
     * @param model
     */
    private void addOrUpdateLabelRecord(ExamScore model) {

        //获取视频
        ExamSubject examSubject = examSubjectMapper.selectByPrimaryKey(model.getSubjectId());
        if (examSubject == null) {
            throw new ServiceException("不存在此试题");
        }
        //获取试题的标签
        String exams = examSubject.getExams();
        if (StringUtils.isNotEmpty(exams)) {

            String[] arr = exams.split(",");

            for (String s : arr) {
                Integer examId = Integer.parseInt(s);
                //查询标签记录里面存不存在此标签
                LabelRecord labelRecord = new LabelRecord();
                labelRecord.setTypeId(examId);
                labelRecord.setType(LabelRecord.TYPE_EXAM);

                //找到原来的记录
                LabelRecord originRecord = labelRecordMapper.selectOne(labelRecord);

                if (originRecord == null) {
                    //直接添加一条用户标签记录
                    LabelRecord addRecord = new LabelRecord();
                    addRecord.setType(LabelRecord.TYPE_EXAM);
                    addRecord.setTypeId(examId);
                    addRecord.setCount(1);
                    addRecord.setCreateTime(new Date());
                    addRecord.setUpdateTime(new Date());

                    labelRecordMapper.insertSelective(addRecord);

                } else {
                    //更新用户标签记录
                    LabelRecord updateRecord = new LabelRecord();
                    updateRecord.setId(originRecord.getId());
                    updateRecord.setCount(originRecord.getCount() + 1);
                    updateRecord.setUpdateTime(new Date());

                    labelRecordMapper.updateByPrimaryKeySelective(updateRecord);
                }
            }
        }
    }
}
