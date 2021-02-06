package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.ExamOption;
import com.yipage.leanmarketing.service.ExamOptionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/05/16.
 */
@RestController
@RequestMapping("/exam/option")
public class ExamOptionController {
    @Resource
    private ExamOptionService examOptionService;


    @PostMapping
    public Result add(@RequestBody ExamOption examOption) {
        examOptionService.save(examOption);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        examOptionService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody ExamOption examOption) {
        examOptionService.update(examOption);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ExamOption examOption = examOptionService.findById(id);
        return ResultGenerator.genSuccessResult(examOption);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ExamOption> list = examOptionService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
