package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.mapper.LessonCollageMapper;
import com.yipage.leanmarketing.mapper.LessonCollageOrderMapper;
import com.yipage.leanmarketing.mapper.LessonCollageRecordMapper;
import com.yipage.leanmarketing.mapper.UserMapper;
import com.yipage.leanmarketing.model.LessonCollage;
import com.yipage.leanmarketing.model.LessonCollageOrder;
import com.yipage.leanmarketing.model.LessonCollageRecord;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.LessonCollageOrderService;
import com.yipage.leanmarketing.utils.DateUtil;
import com.yipage.leanmarketing.utils.MoblieMessageUtil;
import com.yipage.leanmarketing.utils.Tools;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/04/16.
 */
@Service
@Transactional
public class LessonCollageOrderServiceImpl extends AbstractService<LessonCollageOrder> implements LessonCollageOrderService {
    @Resource
    private LessonCollageOrderMapper lessonCollageOrderMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private LessonCollageMapper lessonCollageMapper;

    @Resource
    private LessonCollageRecordMapper lessonCollageRecordMapper;

    @Override
    public Result createOrder(LessonCollageOrder model) {

        String out_trade_no = Tools.creatOrderNumber();

        LessonCollage lessonCollage = lessonCollageMapper.selectByPrimaryKey(model.getLessonId());
        model.setLessonImg(lessonCollage.getCoverImg());
        model.setLessonName(lessonCollage.getName());
        model.setPrice(lessonCollage.getPrice());


        if (model.getIsLeader() == LessonCollageRecord.IS_LEADER) {
            //创建一个随机数
            model.setRecordNum(System.currentTimeMillis() + "");

            //用户选择的开课时间
            Date beginTime = model.getBeginTime();

            //当前时间的当前课程的开课结束时间
            Date endTime = DateUtil.addTime(new Date(), lessonCollage.getDays());

            if (beginTime.getTime() < endTime.getTime()) {
                return ResultGenerator.genFailResult("选择的上课时间小于开团时间,请选择一个往后的时间");
            }

        } else {


            LessonCollageRecord queryRecord = new LessonCollageRecord();
            queryRecord.setRecordNum(model.getRecordNum());
            queryRecord.setIsLeader(LessonCollageRecord.IS_LEADER);

            queryRecord = lessonCollageRecordMapper.selectOne(queryRecord);

            if (model.getUserId() == queryRecord.getLeaderId()) {
                return ResultGenerator.genFailResult("你已是团长了");
            }

            //如果人数超过了,也不能生成订单
            if (queryRecord.getMissNum() <= 0) {
                return ResultGenerator.genFailResult("人数已满,请选择宁外的参团伙伴");

            }
            //如果开课时间到了,也不能生成订单
            if (queryRecord.getEndTime().getTime() < System.currentTimeMillis()) {
                return ResultGenerator.genFailResult("开课时间已到");
            }

            model.setAddress(queryRecord.getAddress());
            model.setBeginTime(queryRecord.getBeginTime());
        }

        User user = userMapper.selectByPrimaryKey(model.getUserId());
        model.setOpenid(user.getWxid());
        model.setUserImg(user.getHeadPortrait());
        if (model.getIsLeader() == LessonCollageRecord.IS_LEADER) {
            model.setLeaderId(user.getId());
        } else {
            model.setLeaderId(model.getLeaderId());
        }


        model.setPayNumber(out_trade_no);
        model.setPayState(LessonCollageOrder.NOPAY);
        model.setPayment("微信支付");

        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        lessonCollageOrderMapper.insertSelective(model);

        return ResultGenerator.genSuccessResult(model);
    }


    @Override
    public void afterPaySucess(LessonCollageOrder order) {

        //获取课程信息
        LessonCollage lessonCollage = lessonCollageMapper.selectByPrimaryKey(order.getLessonId());
        //更新订单
        LessonCollageOrder updateOrder = new LessonCollageOrder();
        updateOrder.setId(order.getId());
        updateOrder.setPayTime(new Date());
        updateOrder.setPayState(LessonCollageOrder.ISPAY);
        lessonCollageOrderMapper.updateByPrimaryKeySelective(updateOrder);

        LessonCollageRecord updateRecord = new LessonCollageRecord();
        //添加一个参团记录
        LessonCollageRecord record = new LessonCollageRecord();

        record.setLessonId(order.getLessonId());
        record.setUserId(order.getUserId());
        record.setLessonName(order.getLessonName());
        record.setLessonImg(lessonCollage.getCoverImg());
        record.setIsLeader(order.getIsLeader());
        record.setLeaderId(order.getLeaderId());
        record.setPrice(order.getPrice());
        record.setStatus(1);  //拼课中
        record.setUserImg(order.getUserImg());
        record.setUserName(order.getUserName());
        record.setBeginTime(order.getBeginTime());
        record.setAddress(order.getAddress());
        record.setRecordNum(order.getRecordNum());
        record.setPhone(order.getUserPhone());
        record.setIsProvideAddress(order.getIsProvideAddress());

        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());

        LessonCollageRecord leaderRecord = null;
        if (order.getIsLeader() == LessonCollageRecord.IS_LEADER) {
            record.setJoinNum(0);
            record.setMissNum(0);
            record.setEndTime(DateUtil.addTime(updateOrder.getPayTime(), lessonCollage.getDays()));
            record.setStatus(1);  //拼团中
            leaderRecord = record;

        } else {
            LessonCollageRecord queryRecord = new LessonCollageRecord();
            queryRecord.setIsLeader(LessonCollageRecord.IS_LEADER);
            queryRecord.setRecordNum(order.getRecordNum());
            leaderRecord = lessonCollageRecordMapper.selectOne(queryRecord);
        }
        lessonCollageRecordMapper.insertSelective(record);

        //更新团长记录信息
        updateRecord.setId(leaderRecord.getId());
        int num = leaderRecord.getJoinNum() + 1;
        updateRecord.setJoinNum(num);
        updateRecord.setMissNum(lessonCollage.getNum() - num);
        updateRecord.setUserName(leaderRecord.getUserName());


        if (num == lessonCollage.getNum()) {  //开团成功

            if (leaderRecord.getIsProvideAddress() == 1) {
                updateRecord.setIsNeedProvideAddress(1);
            } else {
                updateRecord.setIsNeedProvideAddress(2);
            }

            LessonCollageRecord queryRecord = new LessonCollageRecord();
            queryRecord.setRecordNum(leaderRecord.getRecordNum());
            List<LessonCollageRecord> list = lessonCollageRecordMapper.select(queryRecord);

            if (!CollectionUtils.isEmpty(list)) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (LessonCollageRecord r : list) {
                    //更新状态
                    LessonCollageRecord ur = new LessonCollageRecord();
                    ur.setStatus(2);
                    ur.setId(r.getId());

                    lessonCollageRecordMapper.updateByPrimaryKeySelective(ur);
                    if (updateRecord.getIsNeedProvideAddress() == 1) { //已经提供了地址
                        //发送短信通知
                        MoblieMessageUtil.sendTeamSuccessNotice(r.getPhone(), r.getUserName(), r.getLessonName(), simpleDateFormat.format(r.getBeginTime()), r.getAddress());
                    }

                }
            }
        }
        lessonCollageRecordMapper.updateByPrimaryKeySelective(updateRecord);
    }


}
