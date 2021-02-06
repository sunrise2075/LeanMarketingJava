package com.yipage.leanmarketing.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Product;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.model.UserCollectionProduct;
import com.yipage.leanmarketing.service.ProductService;
import com.yipage.leanmarketing.service.UserCollectionProductService;
import com.yipage.leanmarketing.service.UserService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@NeedLoginAnnotation
@RequestMapping("/user/collection/product")
public class UserCollectionProductController {
    @Resource
    private UserCollectionProductService userCollectionProductService;
    @Resource
    private ProductService productService;
    @Resource
    private UserService userService;

    @PostMapping("add")
    public Result add(@RequestBody UserCollectionProduct userCollectionProduct) {

        userCollectionProduct.setCreateTime(new Date());
        userCollectionProduct.setUpdateTime(new Date());
        User user = userService.findBy("wxid", userCollectionProduct.getOpenid());
        if (user != null) {
            userCollectionProduct.setUserId(user.getId());
        }
        userCollectionProductService.save(userCollectionProduct);
        return ResultGenerator.genSuccessResult(userCollectionProduct);
    }

    @DeleteMapping("/{ids}")
    public Result deletes(@PathVariable String ids) {
        userCollectionProductService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 不知道这个怎么写的???
     *
     * @param openid
     * @param productId
     * @return
     */
    @DeleteMapping("/{productId}/{openid}")
    public Result delete(@PathVariable String openid,
                         @PathVariable Integer productId) {
        //找到对应的收藏商品
        UserCollectionProduct userCollectionProduct = new UserCollectionProduct();
        userCollectionProduct.setOpenid(openid);
        userCollectionProduct.setProductId(productId);
        List<UserCollectionProduct> userCollectionProducts = userCollectionProductService.select(userCollectionProduct);
        if (!CollectionUtils.isEmpty(userCollectionProducts)) {
            userCollectionProductService.deleteByIds(userCollectionProducts.get(0).getId() + "");
        }
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes2(@PathVariable String ids) {
        userCollectionProductService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }


//    @GetMapping("/delete2/{productId}/{openid}")
//    public Result delete2(@PathVariable String openid,
//                         @PathVariable Integer productId) {
//        //找到对应的收藏商品
//        UserCollectionProduct userCollectionProduct = new UserCollectionProduct();
//        userCollectionProduct.setOpenid(openid);
//        userCollectionProduct.setProductId(productId);
//        List<UserCollectionProduct> userCollectionProducts = userCollectionProductService.select(userCollectionProduct);
//        if(!CollectionUtils.isEmpty(userCollectionProducts)){
//            userCollectionProductService.deleteByIds(userCollectionProducts.get(0).getId()+"");
//        }
//        return ResultGenerator.genSuccessResult();
//    }


    @PostMapping
    public Result update(@RequestBody UserCollectionProduct userCollectionProduct) {
        userCollectionProductService.update(userCollectionProduct);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        UserCollectionProduct userCollectionProduct = userCollectionProductService.findById(id);
        return ResultGenerator.genSuccessResult(userCollectionProduct);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "openid", required = false) String openid) {

        //条件
        UserCollectionProduct userCollectionProduct = new UserCollectionProduct();

        User user = userService.findBy("wxid", openid);
        if (user != null) {
            userCollectionProduct.setUserId(user.getId());
        }
        //排序
        String orderBy = "update_time desc";

        //分页
        PageHelper.startPage(page, limit, orderBy);

        //查询
        List<UserCollectionProduct> list = userCollectionProductService.select(userCollectionProduct);

        //数据封装
        List<Map<String, Object>> resList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            //查出商品的信息
            for (UserCollectionProduct collectionProduct : list) {
                Map<String, Object> resMap = new HashMap<>();
                resMap.put("collectionProductId", collectionProduct.getId());
                Product product = productService.findById(collectionProduct.getProductId());
                if (product != null) {
                    resMap.put("product", product);
                }
                resList.add(resMap);
            }
        }

        //获得结果集
        PageInfo pageInfo = new PageInfo(resList);
        return MapUtil.PageResult(pageInfo);
    }


}
