package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.VideoCategory;
import com.yipage.leanmarketing.service.VideoCategoryService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@RequestMapping("/video/category")
public class VideoCategoryController {
    @Resource
    private VideoCategoryService videoCategoryService;

    @PostMapping("add")
    public Result add(VideoCategory videoCategory) {
        videoCategory.setCreateTime(new Date());
        videoCategory.setUpdateTime(new Date());
        videoCategoryService.save(videoCategory);
        return ResultGenerator.genSuccessResult();
    }


    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        videoCategoryService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(VideoCategory videoCategory) {
        videoCategory.setUpdateTime(new Date());
        videoCategoryService.update(videoCategory);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        VideoCategory videoCategory = videoCategoryService.findById(id);
        return ResultGenerator.genSuccessResult(videoCategory);
    }

    @GetMapping("/list")
    public Map list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit) {
        String orderBy = "create_time DESC";
        PageHelper.startPage(page, limit, orderBy);
        List<VideoCategory> list = videoCategoryService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }

    @GetMapping("/listAll")
    public Result list() {
        List<VideoCategory> list = videoCategoryService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }


}
