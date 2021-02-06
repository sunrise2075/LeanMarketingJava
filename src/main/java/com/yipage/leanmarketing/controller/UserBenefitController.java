package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.UserBenefit;
import com.yipage.leanmarketing.service.UserBenefitService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/18.
 */
@RestController
@RequestMapping("/user/benefit")
public class UserBenefitController {
    @Resource
    private UserBenefitService userBenefitService;

    @PostMapping("add")
    public Result add(UserBenefit userBenefit) {
        userBenefit.setCreateTime(new Date());
        userBenefit.setUpdateTime(new Date());
        userBenefitService.save(userBenefit);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        userBenefitService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(UserBenefit userBenefit) {
        userBenefit.setUpdateTime(new Date());
        userBenefitService.update(userBenefit);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        UserBenefit userBenefit = userBenefitService.findById(id);
        return ResultGenerator.genSuccessResult(userBenefit);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit) {
        String orderBy = "create_time DESC";
        PageHelper.startPage(page, limit, orderBy);
        List<UserBenefit> list = userBenefitService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }
}
