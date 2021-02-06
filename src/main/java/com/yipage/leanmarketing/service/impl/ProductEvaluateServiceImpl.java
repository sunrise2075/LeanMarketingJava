package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ProductEvaluateMapper;
import com.yipage.leanmarketing.mapper.ProductOrderMapper;
import com.yipage.leanmarketing.mapper.UserMapper;
import com.yipage.leanmarketing.model.ProductEvaluate;
import com.yipage.leanmarketing.model.ProductOrder;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.ProductEvaluateService;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class ProductEvaluateServiceImpl extends AbstractService<ProductEvaluate> implements ProductEvaluateService {
    @Resource
    private ProductEvaluateMapper productEvaluateMapper;

    @Resource
    private ProductOrderMapper productOrderMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public Integer save(ProductEvaluate model) {
        //保存商品评价
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());

        User user = new User();
        user.setWxid(model.getOpenid());
        List<User> userList = userMapper.select(user);
        if (!CollectionUtils.isEmpty(userList)) {
            model.setUserId(userList.get(0).getId());
        }

        //更新订单状态
        ProductOrder productOrder = productOrderMapper.selectByPrimaryKey(model.getOrderId());
        ProductOrder order = new ProductOrder();
        order.setId(productOrder.getId());
        order.setPayState(ProductOrder.ISSUCCESS);
        order.setUpdateTime(new Date());
        order.setEvaluateTime(new Date());
        productOrderMapper.updateByPrimaryKeySelective(order);

        return productEvaluateMapper.insertSelective(model);
    }

    @Override
    public List<ProductEvaluate> select(ProductEvaluate productEvaluate) {
        return productEvaluateMapper.select(productEvaluate);
    }

    @Override
    public Integer selectCount(ProductEvaluate productEvaluate) {
        return productEvaluateMapper.selectCount(productEvaluate);
    }

    @Override
    public List<Map<String, Object>> orderByStarNumLimit2(Integer productId) {
        return productEvaluateMapper.orderByStarNumLimit2(productId);
    }


}
