package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.News;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/01/02.
 */
public interface NewsService extends Service<News> {

    List<News> select(News news);
}
