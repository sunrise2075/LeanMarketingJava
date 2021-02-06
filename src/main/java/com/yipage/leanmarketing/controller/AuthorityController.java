package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Authority;
import com.yipage.leanmarketing.service.AuthorityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/12/20.
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {
    @Resource
    private AuthorityService authorityService;

    @PostMapping
    public Result add(@RequestBody Authority authority) {
        authorityService.save(authority);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        authorityService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody Authority authority) {
        authorityService.update(authority);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Authority authority = authorityService.findById(id);
        return ResultGenerator.genSuccessResult(authority);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Authority> list = authorityService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @GetMapping("listAll")
    public Result listAll() {
        List<Authority> list = authorityService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }
}
