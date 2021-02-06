package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.ProductCategory;
import com.yipage.leanmarketing.service.ProductCategoryService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/14.
 */
@RestController
@RequestMapping("/product/category")
public class ProductCategoryController {
    @Resource
    private ProductCategoryService productCategoryService;

    @PostMapping("add")
    public Result add(ProductCategory productCategory) {
        productCategory.setCreateTime(new Date());
        productCategory.setUpdateTime(new Date());
        productCategoryService.save(productCategory);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        productCategoryService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(ProductCategory productCategory) {
        productCategory.setUpdateTime(new Date());
        productCategoryService.update(productCategory);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ProductCategory productCategory = productCategoryService.findById(id);
        return ResultGenerator.genSuccessResult(productCategory);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit) {
        String orderBy = "create_time DESC";
        PageHelper.startPage(page, limit, orderBy);
        List<ProductCategory> list = productCategoryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 查询所有试题分类(找出不隐藏的试题分类)
     *
     * @return
     */
    @GetMapping("listAll")
    public Result listAll() {
        ProductCategory productCategory = new ProductCategory();

        productCategory.setIsHide(ProductCategory.IS_SHOW);
        List<ProductCategory> list = productCategoryService.select(productCategory);
        return ResultGenerator.genSuccessResult(list);
    }
}
