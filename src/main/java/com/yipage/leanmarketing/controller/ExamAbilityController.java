package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.ExamAbility;
import com.yipage.leanmarketing.service.ExamAbilityService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/06/28.
 */
@RestController
@RequestMapping("/exam/ability")
public class ExamAbilityController {
    @Resource
    private ExamAbilityService examAbilityService;

    @PostMapping("add")
    public Result add(ExamAbility examAbility) {
        examAbility.setCreateTime(new Date());
        examAbility.setUpdateTime(new Date());
        examAbilityService.save(examAbility);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        examAbilityService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(ExamAbility examAbility) {
        examAbilityService.update(examAbility);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ExamAbility examAbility = examAbilityService.findById(id);
        return ResultGenerator.genSuccessResult(examAbility);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        String orderBy = "create_time DESC";
        PageHelper.startPage(page, size, orderBy);
        List<ExamAbility> list = examAbilityService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    @GetMapping("listAll")
    public Result listAll() {
        ExamAbility examAbility = new ExamAbility();
        List<ExamAbility> list = examAbilityService.select(examAbility);
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 根据试题id获取试题能力标签
     *
     * @param subjectId
     * @return
     */
    @GetMapping("getAbilityListById")
    public Result getAbilityListById(Integer subjectId) {

        return ResultGenerator.genSuccessResult(examAbilityService.getAbilityListById(subjectId));
    }


}
