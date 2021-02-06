package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LeanMarketing;
import com.yipage.leanmarketing.service.LeanMarketingService;
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
@RequestMapping("/lean/marketing")
public class LeanMarketingController {
    @Resource
    private LeanMarketingService leanMarketingService;

    @PostMapping("add")
    public Result add(LeanMarketing leanMarketing) {
        leanMarketing.setUpdateTime(new Date());
        leanMarketing.setCreateTime(new Date());
        leanMarketingService.save(leanMarketing);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        leanMarketingService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(LeanMarketing leanMarketing) {
        leanMarketingService.update(leanMarketing);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        LeanMarketing leanMarketing = leanMarketingService.findById(id);
        return ResultGenerator.genSuccessResult(leanMarketing);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size, "order by create_time");
        List<LeanMarketing> list = leanMarketingService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }
}
