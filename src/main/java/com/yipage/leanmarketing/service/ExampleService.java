package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Example;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/16.
 */
public interface ExampleService extends Service<Example> {

    List<Example> select(Example example);
}
