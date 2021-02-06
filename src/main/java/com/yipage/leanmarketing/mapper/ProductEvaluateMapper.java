package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.ProductEvaluate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductEvaluateMapper extends Mapper<ProductEvaluate> {

    List<Map<String, Object>> orderByStarNumLimit2(@Param("productId") Integer productId);
}