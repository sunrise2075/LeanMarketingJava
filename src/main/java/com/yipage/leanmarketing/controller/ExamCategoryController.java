package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.ExamCategory;
import com.yipage.leanmarketing.service.ExamCategoryService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/12.
 */
@RestController
@RequestMapping("/exam/category")
public class ExamCategoryController {
    @Resource
    private ExamCategoryService examCategoryService;
    @Resource
    private HttpServletRequest request;

    @PostMapping("add")
    public Result add(ExamCategory examCategory) {
        examCategory.setCreateTime(new Date());
        examCategory.setUpdateTime(new Date());
        examCategoryService.save(examCategory);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        examCategoryService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(ExamCategory examCategory) {
        examCategory.setUpdateTime(new Date());
        examCategoryService.update(examCategory);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        ExamCategory examCategory = examCategoryService.findById(id);
        return ResultGenerator.genSuccessResult(examCategory);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit) {

        String orderBy = "create_time DESC";
        PageHelper.startPage(page, limit, orderBy);
        List<ExamCategory> list = examCategoryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 查询所有试题分类(找出不隐藏的试题分类)
     *
     * @return
     */
    @GetMapping("listAll")
    public Result listAll() {
        ExamCategory examCategory = new ExamCategory();

        examCategory.setIsHide(ExamCategory.IS_SHOW);

        List<ExamCategory> list = examCategoryService.select(examCategory);
        return ResultGenerator.genSuccessResult(list);
    }
}
