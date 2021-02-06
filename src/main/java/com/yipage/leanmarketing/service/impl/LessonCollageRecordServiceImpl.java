package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.LessonCollageRecordMapper;
import com.yipage.leanmarketing.model.LessonCollageRecord;
import com.yipage.leanmarketing.service.LessonCollageRecordService;
import com.yipage.leanmarketing.utils.MoblieMessageUtil;
import com.yipage.leanmarketing.utils.WeiXinUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/04/15.
 */
@Service
@Transactional
public class LessonCollageRecordServiceImpl extends AbstractService<LessonCollageRecord> implements LessonCollageRecordService {
    @Resource
    private LessonCollageRecordMapper lessonCollageRecordMapper;


    @Override
    public void falseDelete(String ids) {

        String[] split = ids.split(",");
        if (split.length > 0) {
            for (String s : split) {
                Integer id = Integer.parseInt(s);
                LessonCollageRecord updateRecord = new LessonCollageRecord();
                updateRecord.setId(id);
                updateRecord.setIsDelete(1);
                updateRecord.setUpdateTime(new Date());

                lessonCollageRecordMapper.updateByPrimaryKeySelective(updateRecord);
            }
        }

    }


    @Override
    public Integer selectCount(LessonCollageRecord queryRecord) {

        return lessonCollageRecordMapper.selectCount(queryRecord);
    }

    @Override
    public List<LessonCollageRecord> select(LessonCollageRecord queryRecord) {
        return lessonCollageRecordMapper.select(queryRecord);
    }

    /**
     * 定时任务处理拼团失败
     */
    @Override
    public void OverdueTimeLessonCollage() {

        //找出拼课中的拼课记录
        LessonCollageRecord queryLeaderRecord = new LessonCollageRecord();
        queryLeaderRecord.setIsLeader(LessonCollageRecord.IS_LEADER);
        queryLeaderRecord.setStatus(1);

        List<LessonCollageRecord> list = lessonCollageRecordMapper.select(queryLeaderRecord);

        if (!CollectionUtils.isEmpty(list)) {

            for (LessonCollageRecord lessonCollageRecord : list) {

                if (lessonCollageRecord.getEndTime().getTime() < System.currentTimeMillis()) {

                    LessonCollageRecord updateRecord = new LessonCollageRecord();
                    updateRecord.setId(lessonCollageRecord.getId());
                    updateRecord.setStatus(3);
                    updateRecord.setUpdateTime(new Date());

                    lessonCollageRecordMapper.updateByPrimaryKeySelective(updateRecord);

                    //找到这个团里面的所有人
                    LessonCollageRecord queryRecord = new LessonCollageRecord();
                    queryRecord.setRecordNum(lessonCollageRecord.getRecordNum());

                    List<LessonCollageRecord> team = lessonCollageRecordMapper.select(queryRecord);
                    //进行退款操作
                    for (LessonCollageRecord record : team) {

                        String refundFee = record.getPrice().multiply(new BigDecimal(10)).toString();

                        try {
                            WeiXinUtil.doRefund(record.getOrderNum(), refundFee, refundFee);
                            MoblieMessageUtil.sendTeamFailNotice(record.getPhone(), record.getUserName(), record.getLessonName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }
    }

    /**
     * 编辑开课地址并发送短信通知
     *
     * @param lessonCollageRecord
     */
    @Override
    public void updateAndSendMessage(LessonCollageRecord lessonCollageRecord) {

        LessonCollageRecord queryRecord = new LessonCollageRecord();
        queryRecord.setRecordNum(lessonCollageRecord.getRecordNum());
        List<LessonCollageRecord> list = lessonCollageRecordMapper.select(queryRecord);

        if (!CollectionUtils.isEmpty(list)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (LessonCollageRecord record : list) {
                LessonCollageRecord updateRecord = new LessonCollageRecord();
                if (record.getIsLeader() == 1) {
                    updateRecord.setIsNeedProvideAddress(1);  //设置成已经设置了地址
                }
                updateRecord.setId(record.getId());
                updateRecord.setAddress(record.getAddress());

                lessonCollageRecordMapper.updateByPrimaryKeySelective(updateRecord);

                MoblieMessageUtil.sendTeamSuccessNotice(record.getPhone(), record.getUserName(), record.getLessonName(), simpleDateFormat.format(record.getBeginTime()), record.getAddress());
            }

        }
    }
}
