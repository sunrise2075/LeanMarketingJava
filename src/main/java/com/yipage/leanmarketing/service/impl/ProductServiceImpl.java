package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ProductCategoryMapper;
import com.yipage.leanmarketing.mapper.ProductMapper;
import com.yipage.leanmarketing.model.Product;
import com.yipage.leanmarketing.model.ProductCategory;
import com.yipage.leanmarketing.service.ProductService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class ProductServiceImpl extends AbstractService<Product> implements ProductService {
    @Resource
    private ProductMapper productMapper;

    @Resource
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public Integer save(Product model) {
        model.setUpdateTime(new Date());
        model.setCreateTime(new Date());
        ProductCategory productCategory = productCategoryMapper.selectByPrimaryKey(model.getCategoryId());
        if (productCategory != null) {
            model.setCategoryName(productCategory.getName());
        }
        return super.save(model);
    }

    @Override
    public List<Product> select(Product product) {
        return productMapper.select(product);
    }

    @Override
    public List<Product> query(Integer categoryId, Integer isFree, Integer limit) {
        return productMapper.query(categoryId, isFree, limit);
    }


    @Override
    public List<Product> orderBySalesNum() {
        return productMapper.orderBySalesNum();
    }

    @Override
    public List<Product> latestUpProduct() {
        return productMapper.latestUpProduct();
    }

    @Override
    public List<Product> randomRecommend(Integer limit) {

        Product product = new Product();
        product.setState(Product.STATE_UP);
        product.setIsHide(Product.IS_SHOW);
        List<Product> list = productMapper.select(product);

        if (!CollectionUtils.isEmpty(list)) {
            List<Product> randomRecommendList = new ArrayList<>();
            if (list.size() > limit) {
                Integer[] arr = new Integer[list.size()];
                for (int i = 0; i < list.size(); i++) {

                    arr[i] = list.get(i).getId();

                }
                int a = 0;
                while (a < limit) {
                    a++;
                    int j = (int) (Math.random() * arr.length);
                    randomRecommendList.add(list.get(j));
                }
                return randomRecommendList;
            } else {
                return list;
            }

        }
        return null;
    }


}
