package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ConsumeRecordMapper;
import com.yipage.leanmarketing.mapper.UserMapper;
import com.yipage.leanmarketing.model.ConsumeRecord;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.ConsumeRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/18.
 */
@Service
@Transactional
public class ConsumeRecordServiceImpl extends AbstractService<ConsumeRecord> implements ConsumeRecordService {
    @Resource
    private ConsumeRecordMapper consumeRecordMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public Map<String, Object> revenueStreams(Integer page, Integer limit, Integer year, Integer month, Integer day, Integer type, String address) {

        Map<String, Object> map = new HashMap<>();
        //获取总金额
        BigDecimal totalMoney = consumeRecordMapper.getTotalRevenue(address);
        if (totalMoney == null) {
            totalMoney = BigDecimal.ZERO;
        }
        map.put("totalMoney", totalMoney);
        //获取总人数
        Integer totalNumber = consumeRecordMapper.getTotalNumber(address);
        map.put("totalNumber", totalNumber);

        Integer count = consumeRecordMapper.getListCount(year, month, day, type, address);
        map.put("count", count);
        Integer beginIndex = (page - 1) * limit;
        List<ConsumeRecord> list = consumeRecordMapper.getList(beginIndex, limit, year, month, day, type, address);
        map.put("data", list);
        //获取条件查询的总收入和总人数
        BigDecimal money = consumeRecordMapper.getRevenue(year, month, day, type, address);
        if (money == null) {
            money = BigDecimal.ZERO;
        }
        map.put("money", money);
        Integer number = consumeRecordMapper.getNumber(year, month, day, type, address);
        map.put("number", number);
        return map;
    }

    @Override
    public List<ConsumeRecord> select(ConsumeRecord consumeRecord) {
        return consumeRecordMapper.select(consumeRecord);
    }

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
    @Override
    public Map<String, Object> dealerRevenueStreams(Integer page, Integer limit, Integer superiorId, Integer year, Integer month, Integer day, Integer type) {

        Map<String, Object> map = new HashMap<>();
        //总收入
        BigDecimal totalMoney = consumeRecordMapper.dealerRevenueStreams(superiorId);
        map.put("totalMoney", totalMoney);
        //下级总人数
        User user = new User();
        user.setSuperiorId(superiorId);
        Integer totalNum = userMapper.selectCount(user);
        map.put("totalNum", totalNum);

        //分页
        Integer count = consumeRecordMapper.getListDealerRevenueCount(year, month, day, type, superiorId, null);
        map.put("count", count);
        Integer beginIndex = (page - 1) * limit;
        List<ConsumeRecord> list = consumeRecordMapper.getListDealerRevenue(beginIndex, limit, year, month, day, type, superiorId, null);
        map.put("data", list);

        //获取条件查询的总收入和总人数
        BigDecimal money = consumeRecordMapper.getDealerRevenue(year, month, day, type, superiorId);
        if (money == null) {
            money = BigDecimal.ZERO;
        }
        map.put("money", money);
        Integer number = consumeRecordMapper.getDealerNumber(year, month, day, type, superiorId);
        map.put("number", number);

        return map;
    }

    @Override
    public Map<String, Object> list(Integer page, Integer limit, String address, Integer superiorId, Integer type) {
        Map<String, Object> map = new HashMap<>();
        //分页
        Integer count = consumeRecordMapper.getConsumeRecordCount(superiorId, address, type);
        map.put("count", count);
        Integer beginIndex = null;
        if (page != null) {
            beginIndex = (page - 1) * limit;
        }
        List<ConsumeRecord> list = consumeRecordMapper.getConsumeRecordList(beginIndex, limit, superiorId, address, type);
        map.put("data", list);
        map.put("status", 1);
        return map;
    }
}
