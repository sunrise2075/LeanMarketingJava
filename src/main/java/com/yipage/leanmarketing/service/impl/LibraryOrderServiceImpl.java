package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.*;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.LibraryOrder;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.LibraryOrderService;
import com.yipage.leanmarketing.utils.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/14.
 */
@Service
@Transactional
public class LibraryOrderServiceImpl extends AbstractService<LibraryOrder> implements LibraryOrderService {
    @Resource
    private LibraryOrderMapper libraryOrderMapper;

    @Resource
    private LibraryDownloadRecordMapper libraryDownloadRecordMapper;
    @Resource
    private LibraryMapper libraryMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ConsumeRecordMapper consumeRecordMapper;

    @Override
    public Integer save(LibraryOrder model) {
        String out_trade_no = Tools.creatOrderNumber();
        User u = new User();
        u.setWxid(model.getOpenid());
        User user = userMapper.selectOne(u);
        if (user != null) {
            model.setUserId(user.getId());
            model.setUserName(user.getNickname());
        }
        model.setPayNumber(out_trade_no);
        model.setPayState(LibraryOrder.NOPAY);
        model.setPayment("微信支付");
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        Integer result = libraryOrderMapper.insert(model);
        if (result > 0) {
            return model.getId();
        }
        return null;
    }

    @Override
    public List<LibraryOrder> select(LibraryOrder libraryOrder) {
        return libraryOrderMapper.select(libraryOrder);
    }

    @Override
    public void afterPaySucess(LibraryOrder libraryOrder) {
        //更新订单
        LibraryOrder model = new LibraryOrder();
        model.setPayState(LibraryOrder.ISPAY);
        model.setPayTime(new Date());
        model.setUpdateTime(new Date());
        model.setId(libraryOrder.getId());
        model.setPayment(libraryOrder.getPayment());
        libraryOrderMapper.updateByPrimaryKeySelective(model);

        //找到用户信息
        User user = userMapper.selectByPrimaryKey(libraryOrder.getUserId());
        //找出文库信息
//        Library library = libraryMapper.selectByPrimaryKey(libraryOrder.getLibraryId());

        //生成下载记录
//        LibraryDownloadRecord libraryDownloadRecord = new LibraryDownloadRecord();
//        libraryDownloadRecord.setOpenid(user.getWxid());
//        libraryDownloadRecord.setUserId(user.getId());
//        libraryDownloadRecord.setLibraryId(libraryOrder.getLibraryId());
//        libraryDownloadRecord.setUrl(library.getUrl());
//        libraryDownloadRecord.setUpdateTime(new Date());
//        libraryDownloadRecord.setCreateTime(new Date());
//
//        libraryDownloadRecord.setLibraryTitle(library.getTitle());
//        libraryDownloadRecord.setFileType(library.getFileType());
//        libraryDownloadRecord.setFileSize(library.getFileSize());
//
//        libraryDownloadRecordMapper.insertSelective(libraryDownloadRecord);

        //生成消费记录
        ConsumeRecord consumeRecord = new ConsumeRecord();
        consumeRecord.setCreateTime(new Date());
        consumeRecord.setAddress(user.getRegisteredAddress());
        consumeRecord.setOrderNumber(libraryOrder.getPayNumber());
        consumeRecord.setPayMoney(libraryOrder.getPayMoney());
        consumeRecord.setPayTime(new Date());
        consumeRecord.setType(ConsumeRecord.CONSUME_TYPE_LIBARAT);
        consumeRecord.setUpdateTime(new Date());
        consumeRecord.setUserGrade(user.getMemberLevel());
        consumeRecord.setUserId(user.getId());
        consumeRecord.setOpenid(user.getWxid());
        consumeRecord.setUserName(user.getNickname());
        consumeRecord.setUserHeadPortrait(user.getHeadPortrait());
        consumeRecord.setSuperiorId(user.getSuperiorId());

        consumeRecordMapper.insertSelective(consumeRecord);
    }
}
