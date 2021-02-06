package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Black;
import com.yipage.leanmarketing.service.BlackService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/01/17.
 */
@RestController
@RequestMapping("/black")
public class BlackController {
    @Resource
    private BlackService blackService;

    @PostMapping("add")
    public Result add(Black black) {
        //看一下此手机号是否存在
        Black black1 = blackService.findBy("phone", black.getPhone());
        if (black1 != null) {
            return ResultGenerator.genFailResult("此手机号已存在,添加失败");
        }
        black.setCreateTime(new Date());
        black.setUpdateTime(new Date());
        blackService.save(black);

        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        blackService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody Black black) {
        black.setUpdateTime(new Date());
        blackService.update(black);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Black black = blackService.findById(id);
        return ResultGenerator.genSuccessResult(black);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer limit) {
        PageHelper.startPage(page, limit);
        List<Black> list = blackService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }
}
