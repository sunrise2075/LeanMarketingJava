package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.annotation.NeedLoginAnnotation;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.ProductEvaluate;
import com.yipage.leanmarketing.model.User;
import com.yipage.leanmarketing.service.ProductEvaluateService;
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
@RequestMapping("/product/evaluate")
public class ProductEvaluateController {
    @Resource
    private ProductEvaluateService productEvaluateService;

    @Resource
    private UserService userService;

    @NeedLoginAnnotation
    @PostMapping("add")
    public Result add(@RequestBody ProductEvaluate productEvaluate) {

        productEvaluateService.save(productEvaluate);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        productEvaluateService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping("update")
    public Result update(ProductEvaluate productEvaluate) {
        productEvaluate.setUpdateTime(new Date());
        productEvaluateService.update(productEvaluate);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ProductEvaluate productEvaluate = productEvaluateService.findById(id);

        return ResultGenerator.genSuccessResult(productEvaluate);
    }

    /**
     * 分页并条件查询商品评价
     *
     * @param page
     * @param limit
     * @param productId
     * @return
     */
    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "productId", required = false) Integer productId) {
        String orderBy = "create_time desc";
        PageHelper.startPage(page, limit, orderBy);
        ProductEvaluate productEvaluate = new ProductEvaluate();
        if (productId != null) {
            productEvaluate.setProductId(productId);
        }
        List<ProductEvaluate> list = productEvaluateService.select(productEvaluate);
        List<Map<String, Object>> list2 = new ArrayList<>();
        PageInfo pageInfo = new PageInfo(list);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProductEvaluate evaluate : list) {
                Map<String, Object> map = new HashMap<>();
                User user = userService.findBy("wxid", evaluate.getOpenid());
                if (user != null) {
                    map.put("userName", user.getNickname());
                    map.put("headPortrait", user.getHeadPortrait());
                }
                map.put("userId", evaluate.getUserId());
                map.put("openid", evaluate.getOpenid());
                map.put("orderId", evaluate.getOrderId());
                map.put("createTime", evaluate.getCreateTime());
                map.put("description", evaluate.getDescription());
                map.put("evaluateImg", evaluate.getEvaluateImg());
                map.put("id", evaluate.getId());
                map.put("productId", evaluate.getProductId());
                map.put("starNum", evaluate.getStarNum());
                map.put("updateTime", evaluate.getUpdateTime());
                list2.add(map);
            }
        }
        pageInfo.setList(list2);
        return MapUtil.PageResult(pageInfo);
    }


}
