package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ProductCategory;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/14.
 */
public interface ProductCategoryService extends Service<ProductCategory> {

    List<ProductCategory> select(ProductCategory productCategory);
}
