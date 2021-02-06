package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.LibraryCategory;
import com.yipage.leanmarketing.service.LibraryCategoryService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/13.
 */
@RestController
@RequestMapping("/library/category")
public class LibraryCategoryController {
    @Resource
    private LibraryCategoryService libraryCategoryService;

    @PostMapping("add")
    public Result add(LibraryCategory libraryCategory) {
        libraryCategory.setCreateTime(new Date());
        libraryCategory.setUpdateTime(new Date());
        libraryCategoryService.save(libraryCategory);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        libraryCategoryService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(LibraryCategory libraryCategory) {
        libraryCategoryService.update(libraryCategory);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        LibraryCategory libraryCategory = libraryCategoryService.findById(id);
        return ResultGenerator.genSuccessResult(libraryCategory);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit) {
        String orderBy = "sort";
        PageHelper.startPage(page, limit, orderBy);
        List<LibraryCategory> list = libraryCategoryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 查询所有文库分类(找出不隐藏的文库分类)
     *
     * @return
     */
    @GetMapping("listAll")
    public Result listAll() {
        LibraryCategory libraryCategory = new LibraryCategory();
        libraryCategory.setIsHide(LibraryCategory.IS_SHOW);
        List<LibraryCategory> list = libraryCategoryService.select(libraryCategory);
        return ResultGenerator.genSuccessResult(list);
    }

    @GetMapping("select")
    public Result select() {
        LibraryCategory libraryCategory = new LibraryCategory();
        libraryCategory.setIsHide(LibraryCategory.IS_SHOW);
        List<LibraryCategory> list = libraryCategoryService.select(libraryCategory);
        return ResultGenerator.genSuccessResult(list);
    }
}
