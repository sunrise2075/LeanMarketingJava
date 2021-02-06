package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ProductOrder;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/14.
 */
public interface ProductOrderService extends Service<ProductOrder> {

    List<ProductOrder> select(ProductOrder productOrder);

    Map<String, Object> selectAllOrderStateNum(String openid);

    Map<String, Object> index(Integer payState, Integer page, Integer limit, String startTime, String endTime, String payNumber, String userName, String productName, String openid);

    List<Map<String, Object>> exportExcel(Integer payState, String startTime, String endTime, String payNumber, String userName, String productName);

    Integer send(ProductOrder productOrder);
}
