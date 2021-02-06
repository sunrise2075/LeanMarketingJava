package com.yipage.leanmarketing.mapper;

import com.yipage.leanmarketing.core.Mapper;
import com.yipage.leanmarketing.model.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends Mapper<Product> {
    List<Product> orderBySalesNum();

    List<Product> latestUpProduct();

    List<Product> query(@Param("categoryId") Integer categoryId,
                        @Param("isFree") Integer isFree, @Param("limit") Integer limit);
}