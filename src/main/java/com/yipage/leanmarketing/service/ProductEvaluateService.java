package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ProductEvaluate;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface ProductEvaluateService extends Service<ProductEvaluate> {

    Integer selectCount(ProductEvaluate productEvaluate);

    List<Map<String, Object>> orderByStarNumLimit2(Integer productId);

    List<ProductEvaluate> select(ProductEvaluate productEvaluate);
}
