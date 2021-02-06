package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.MarketingConsultant;
import com.yipage.leanmarketing.service.MarketingConsultantService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/03/16.
 */
@RestController
@RequestMapping("/marketing/consultant")
public class MarketingConsultantController {
    @Resource
    private MarketingConsultantService marketingConsultantService;

    @PostMapping("add")
    public Result add(MarketingConsultant marketingConsultant) {
        marketingConsultant.setCreateTime(new Date());
        marketingConsultant.setUpdateTime(new Date());
        marketingConsultantService.save(marketingConsultant);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        marketingConsultantService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(MarketingConsultant marketingConsultant) {
        marketingConsultantService.update(marketingConsultant);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        MarketingConsultant marketingConsultant = marketingConsultantService.findById(id);
        return ResultGenerator.genSuccessResult(marketingConsultant);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size, "order by create_time");
        List<MarketingConsultant> list = marketingConsultantService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }
}
