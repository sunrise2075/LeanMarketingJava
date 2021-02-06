package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Dealer;
import com.yipage.leanmarketing.service.DealerService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/03/19.
 */
@RestController
@RequestMapping("/dealer")
public class DealerController {
    @Resource
    private DealerService dealerService;

    @PostMapping("add")
    public Result add(Dealer dealer) {
        return dealResult(dealerService.save(dealer));
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        dealerService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(Dealer dealer) {
        return dealResult(dealerService.update(dealer));
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Dealer dealer = dealerService.findById(id);
        return ResultGenerator.genSuccessResult(dealer);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Dealer> list = dealerService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    @GetMapping("listAll")
    public Result list() {

        List<Dealer> list = dealerService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

    private Result dealResult(Integer result) {
        if (result == 1) {
            return ResultGenerator.genSuccessResult();
        } else if (result == 2) {
            return ResultGenerator.genFailResult("此手机存在黑名单里面");
        } else if (result == 3) {
            return ResultGenerator.genFailResult("此手机存在子用户表中");
        } else if (result == 4) {
            return ResultGenerator.genFailResult("此手机存在员工管理中");
        } else if (result == 5) {
            return ResultGenerator.genFailResult("此手机存在代理商中");
        } else if (result == 6) {
            return ResultGenerator.genFailResult("此手机存在经销商中");
        } else {
            return ResultGenerator.genFailResult("此手机存在会员表中");

        }
    }
}
