package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LessonCollageRule;
import com.yipage.leanmarketing.service.LessonCollageRuleService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/04/15.
 */
@RestController
@RequestMapping("/lesson/collage/rule")
public class LessonCollageRuleController {
    @Resource
    private LessonCollageRuleService lessonCollageRuleService;

    @PostMapping("add")
    public Result add(LessonCollageRule lessonCollageRule) {
        lessonCollageRuleService.save(lessonCollageRule);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        lessonCollageRuleService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(LessonCollageRule lessonCollageRule) {
        lessonCollageRuleService.update(lessonCollageRule);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        LessonCollageRule lessonCollageRule = lessonCollageRuleService.findById(id);
        return ResultGenerator.genSuccessResult(lessonCollageRule);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<LessonCollageRule> list = lessonCollageRuleService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }
}
