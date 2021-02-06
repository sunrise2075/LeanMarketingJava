package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ConsumeRecordMapper;
import com.yipage.leanmarketing.mapper.ExamOrderMapper;
import com.yipage.leanmarketing.mapper.UserMapper;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.ExamOrder;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.ExamOrderService;
import com.yipage.leanmarketing.utils.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/13.
 */
@Service
@Transactional
public class ExamOrderServiceImpl extends AbstractService<ExamOrder> implements ExamOrderService {
    @Resource
    private ExamOrderMapper examOrderMapper;

    @Resource
    private UserMapper userMapper;
    @Resource
    private ConsumeRecordMapper consumeRecordMapper;

    @Override
    public List<ExamOrder> select(ExamOrder examOrder) {
        return examOrderMapper.select(examOrder);
    }

    @Override
    public void afterPaySucess(ExamOrder examOrder) {
        //更新订单
        ExamOrder model = new ExamOrder();
        model.setPayState(ExamOrder.ISPAY);
        model.setPayTime(new Date());
        model.setUpdateTime(new Date());
        model.setId(examOrder.getId());

        examOrderMapper.updateByPrimaryKeySelective(model);

        //找到用户信息
        User user = userMapper.selectByPrimaryKey(examOrder.getUserId());

        //生成消费记录
        ConsumeRecord consumeRecord = new ConsumeRecord();
        consumeRecord.setCreateTime(new Date());
        consumeRecord.setOrderNumber(examOrder.getPayNumber());
        consumeRecord.setAddress(user.getRegisteredAddress());
        consumeRecord.setPayMoney(examOrder.getPayMoney());
        consumeRecord.setPayTime(new Date());
        consumeRecord.setType(ConsumeRecord.CONSUME_TYPE_EXAM);
        consumeRecord.setUpdateTime(new Date());
        consumeRecord.setUserGrade(user.getMemberLevel());
        consumeRecord.setUserId(user.getId());
        consumeRecord.setOpenid(user.getWxid());
        consumeRecord.setUserName(user.getNickname());
        consumeRecord.setUserHeadPortrait(user.getHeadPortrait());
        consumeRecord.setSuperiorId(user.getSuperiorId());

        consumeRecordMapper.insertSelective(consumeRecord);
    }

    @Override
    public Integer save(ExamOrder model) {

        String out_trade_no = Tools.creatOrderNumber();
        User user = new User();
        user.setWxid(model.getOpenid());
        User u = userMapper.selectOne(user);
        if (u != null) {
            model.setUserId(u.getId());
            model.setUserName(u.getNickname());
        }
        model.setPayNumber(out_trade_no);
        model.setPayState(ExamOrder.NOPAY);
        model.setPayment("微信支付");
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        Integer result = examOrderMapper.insert(model);
        if (result > 0) {
            return model.getId();
        }
        return null;
    }


}
