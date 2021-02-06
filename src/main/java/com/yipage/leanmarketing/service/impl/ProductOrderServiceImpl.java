package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ProductOrderMapper;
import com.yipage.leanmarketing.mapper.UserMapper;
import com.yipage.leanmarketing.model.ProductOrder;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.ProductOrderService;
import com.yipage.leanmarketing.utils.DateUtil;
import com.yipage.leanmarketing.utils.Tools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/14.
 */
@Service
@Transactional
public class ProductOrderServiceImpl extends AbstractService<ProductOrder> implements ProductOrderService {
    @Resource
    private ProductOrderMapper productOrderMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<ProductOrder> select(ProductOrder productOrder) {
        return productOrderMapper.select(productOrder);
    }


    @Override
    public Integer save(ProductOrder model) {
        String out_trade_no = Tools.creatOrderNumber();
        User u = new User();
        u.setWxid(model.getOpenid());
        User user = userMapper.selectOne(u);
        if (user != null) {
            model.setUserId(user.getId());
            model.setUserName(user.getNickname());
        }
        model.setPayNumber(out_trade_no);
        model.setPayState(ProductOrder.NOPAY);
        model.setPayment("微信支付");
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        Integer result = productOrderMapper.insert(model);
        if (result > 0) {
            return model.getId();
        }
        return null;
    }

    /**
     * 后台订单首页
     *
     * @return
     */
    @Override
    public Map<String, Object> selectAllOrderStateNum(String openid) {

        //查出所有订单状态的数量
        Map<String, Object> resMap = productOrderMapper.selectAllOrderStateNum(openid);

        return resMap;
    }

    @Override
    public Map<String, Object> index(Integer payState, Integer page, Integer limit, String startTime, String endTime, String payNumber, String userName, String productName, String openid) {

        Map<String, Object> resMap = new HashMap<>();
        Integer count = productOrderMapper.count(payState, startTime, endTime, payNumber, userName, productName, openid);
        resMap.put("count", count);
        int beginIndex = (page - 1) * limit;
        List<Map<String, Object>> list = productOrderMapper.listPager(beginIndex, limit, payState, startTime, endTime, payNumber, userName, productName, openid);
        resMap.put("data", list);
        resMap.put("status", 1);
        return resMap;

    }

    /**
     * 导出订单
     *
     * @param payState
     * @param startTime
     * @param endTime
     * @param payNumber
     * @param userName
     * @param productName
     * @return
     */
    @Override
    public List<Map<String, Object>> exportExcel(Integer payState, String startTime, String endTime, String payNumber, String userName, String productName) {
        return productOrderMapper.exportExcel(payState, startTime, endTime, payNumber, userName, productName);
    }

    /**
     * 发货
     *
     * @param productOrder
     * @return
     */
    @Override
    public Integer send(ProductOrder productOrder) {
        productOrder.setUpdateTime(new Date());
        productOrder.setPayState(ProductOrder.NEEDRECEIVED);
        productOrder.setSendTime(new Date());
        productOrder.setAutoReceiveTime(DateUtil.getFetureDate(7));
        return productOrderMapper.updateByPrimaryKeySelective(productOrder);
    }


}
