package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ProductCategoryMapper;
import com.yipage.leanmarketing.model.ProductCategory;
import com.yipage.leanmarketing.service.ProductCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/14.
 */
@Service
@Transactional
public class ProductCategoryServiceImpl extends AbstractService<ProductCategory> implements ProductCategoryService {
    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> select(ProductCategory productCategory) {
        return productCategoryMapper.select(productCategory);
    }
}
