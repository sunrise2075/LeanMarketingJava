package com.yipage.leanmarketing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转控制层
 */
@Controller
@RequestMapping(value = "page")
public class PageController {

    @GetMapping("/{page}")
    public String page(@PathVariable("page") String page) {

        return "backstage/" + page;
    }
}
