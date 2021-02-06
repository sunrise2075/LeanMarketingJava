package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Label;
import com.yipage.leanmarketing.service.LabelService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/06/10.
 */
@RestController
@RequestMapping("/label")
public class LabelController {
    @Resource
    private LabelService labelService;

    @PostMapping("add")
    public Result add(Label label) {
        label.setCreateTime(new Date());
        label.setUpdateTime(new Date());
        labelService.save(label);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        labelService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(Label label) {
        labelService.update(label);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Label label = labelService.findById(id);
        return ResultGenerator.genSuccessResult(label);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        String orderBy = "create_time DESC";
        PageHelper.startPage(page, size, orderBy);
        List<Label> list = labelService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    @GetMapping("listAll")
    public Result listAll() {
        Label label = new Label();
        List<Label> list = labelService.select(label);
        return ResultGenerator.genSuccessResult(list);
    }

}
