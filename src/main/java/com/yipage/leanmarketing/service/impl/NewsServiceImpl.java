package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.NewsMapper;
import com.yipage.leanmarketing.model.News;
import com.yipage.leanmarketing.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/01/02.
 */
@Service
@Transactional
public class NewsServiceImpl extends AbstractService<News> implements NewsService {
    @Resource
    private NewsMapper newsMapper;

    @Override
    public List<News> select(News news) {
        return newsMapper.select(news);
    }
}
