package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ConsumeRecord;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/18.
 */
public interface ConsumeRecordService extends Service<ConsumeRecord> {

    /**
     * 代理商的收益流水
     *
     * @param page
     * @param limit
     * @param year
     * @param month
     * @param day
     * @param type
     * @param address
     * @return
     */
    Map<String, Object> revenueStreams(Integer page, Integer limit, Integer year, Integer month, Integer day, Integer type, String address);

    List<ConsumeRecord> select(ConsumeRecord consumeRecord);

    /**
     * 经销商的收益流水
     *
     * @param page
     * @param limit
     * @param year
     * @param month
     * @param day
     * @param type
     * @return
     */
    Map<String, Object> dealerRevenueStreams(Integer page, Integer limit, Integer superiorId, Integer year, Integer month, Integer day, Integer type);

    Map<String, Object> list(Integer page, Integer limit, String address, Integer superiorId, Integer type);
}
