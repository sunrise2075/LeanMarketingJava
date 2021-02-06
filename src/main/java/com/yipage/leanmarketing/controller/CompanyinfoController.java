package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Companyinfo;
import com.yipage.leanmarketing.service.CompanyinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/03/16.
 */
@RestController
@RequestMapping("/companyinfo")
public class CompanyinfoController {
    @Resource
    private CompanyinfoService companyinfoService;

    @PostMapping("add")
    public Result add(Companyinfo companyinfo) {
        companyinfo.setCreateTime(new Date());
        companyinfo.setUpdateTime(new Date());
        companyinfoService.save(companyinfo);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        companyinfoService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(Companyinfo companyinfo) {
        companyinfoService.update(companyinfo);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Companyinfo companyinfo = companyinfoService.findById(id);
        return ResultGenerator.genSuccessResult(companyinfo);
    }

    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Companyinfo> list = companyinfoService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
