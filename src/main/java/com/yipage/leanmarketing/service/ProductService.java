package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Product;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface ProductService extends Service<Product> {

    List<Product> orderBySalesNum();

    List<Product> latestUpProduct();

    List<Product> randomRecommend(Integer limit);

    List<Product> select(Product product);

    List<Product> query(Integer categoryId, Integer isFree, Integer limit);
}
