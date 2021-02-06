package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.News;
import com.yipage.leanmarketing.service.NewsService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2019/01/02.
 */
@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    @PostMapping("add")
    public Result add(News news) {
        news.setCreateTime(new Date());
        news.setUpdateTime(new Date());
        newsService.save(news);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        newsService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(News news) {
        news.setUpdateTime(new Date());
        newsService.update(news);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        News news = newsService.findById(id);
        return ResultGenerator.genSuccessResult(news);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit) {
        PageHelper.startPage(page, limit, "update_time desc");
        List<News> list = newsService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }
}
