package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ConsumeRecordMapper;
import com.yipage.leanmarketing.mapper.UserMapper;
import com.yipage.leanmarketing.mapper.VideoMapper;
import com.yipage.leanmarketing.mapper.VideoOrderMapper;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.model.Video;
import com.yipage.leanmarketing.model.VideoOrder;
import com.yipage.leanmarketing.service.VideoOrderService;
import com.yipage.leanmarketing.utils.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/12.
 */
@Service
@Transactional
public class VideoOrderServiceImpl extends AbstractService<VideoOrder> implements VideoOrderService {
    @Resource
    private VideoOrderMapper videoOrderMapper;
    @Resource
    private VideoMapper videoMapper;


    @Resource
    private UserMapper userMapper;
    @Resource
    private ConsumeRecordMapper consumeRecordMapper;

    /**
     * 创建订单
     *
     * @param model
     * @return 订单id
     */
    @Override
    public Integer save(VideoOrder model) {

        String out_trade_no = Tools.creatOrderNumber();
        User u = new User();
        u.setWxid(model.getOpenid());
        User user = userMapper.selectOne(u);
        if (user != null) {
            model.setUserId(user.getId());
            model.setUserName(user.getNickname());
        }
        model.setPayNumber(out_trade_no);
        model.setPayState(VideoOrder.NOPAY);
        model.setPaymemt("微信支付");
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        Integer result = videoOrderMapper.insert(model);
        if (result > 0) {
            return model.getId();
        } else {
            return 0;
        }

    }

    /**
     * 根据条件查所有订单
     *
     * @param videoOrder
     * @return
     */
    @Override
    public List<VideoOrder> select(VideoOrder videoOrder) {
        return videoOrderMapper.select(videoOrder);
    }

    @Override
    public Integer selectCount(VideoOrder videoOrder) {
        return videoOrderMapper.selectCount(videoOrder);
    }

    @Override
    public void afterPaySucess(VideoOrder videoOrder) {

        //更新订单
        VideoOrder updateOrder = new VideoOrder();
        updateOrder.setId(videoOrder.getId());
        updateOrder.setPayTime(new Date());
        updateOrder.setPayState(VideoOrder.ISPAY);
        videoOrderMapper.updateByPrimaryKeySelective(updateOrder);

        //查找视频
        Video v = videoMapper.selectByPrimaryKey(videoOrder.getVideoId());
        //更新视频销售量
        if (v != null) {
            Video video = new Video();
            video.setId(v.getId());
            video.setSalesNum(v.getSalesNum() + 1);
            video.setUpdateTime(new Date());
            videoMapper.updateByPrimaryKeySelective(video);
        }


        //找到用户信息
        User user = userMapper.selectByPrimaryKey(videoOrder.getUserId());

        //生成消费记录
        ConsumeRecord consumeRecord = new ConsumeRecord();
        consumeRecord.setOpenid(user.getWxid());
        consumeRecord.setAddress(user.getRegisteredAddress());
        consumeRecord.setCreateTime(new Date());
        consumeRecord.setOrderNumber(videoOrder.getPayNumber());
        consumeRecord.setPayMoney(videoOrder.getPayMoney());
        consumeRecord.setPayTime(new Date());
        consumeRecord.setType(ConsumeRecord.CONSUME_TYPE_VIDEO);
        consumeRecord.setUpdateTime(new Date());
        consumeRecord.setUserGrade(user.getMemberLevel());
        consumeRecord.setUserId(user.getId());
        consumeRecord.setUserName(user.getNickname());
        consumeRecord.setUserHeadPortrait(user.getHeadPortrait());
        consumeRecord.setSuperiorId(user.getSuperiorId());

        consumeRecordMapper.insertSelective(consumeRecord);
    }


}
