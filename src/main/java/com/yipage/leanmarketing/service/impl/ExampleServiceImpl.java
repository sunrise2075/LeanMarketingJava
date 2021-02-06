package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.ExampleMapper;
import com.yipage.leanmarketing.model.Example;
import com.yipage.leanmarketing.service.ExampleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2019/03/16.
 */
@Service
@Transactional
public class ExampleServiceImpl extends AbstractService<Example> implements ExampleService {
    @Resource
    private ExampleMapper exampleMapper;

    @Override
    public List<Example> select(Example example) {
        return exampleMapper.select(example);
    }
}
