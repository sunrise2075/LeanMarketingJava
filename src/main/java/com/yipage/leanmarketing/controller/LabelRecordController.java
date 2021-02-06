package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LabelRecord;
import com.yipage.leanmarketing.service.LabelRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2019/06/10.
 */
@RestController
@RequestMapping("/label/record")
public class LabelRecordController {
    @Resource
    private LabelRecordService labelRecordService;

    @PostMapping
    public Result add(@RequestBody LabelRecord labelRecord) {
        labelRecordService.save(labelRecord);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        labelRecordService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody LabelRecord labelRecord) {
        labelRecordService.update(labelRecord);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        LabelRecord labelRecord = labelRecordService.findById(id);
        return ResultGenerator.genSuccessResult(labelRecord);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<LabelRecord> list = labelRecordService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
