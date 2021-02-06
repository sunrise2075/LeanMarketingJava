package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Label;

import java.util.List;


/**
 * Created by CodeGenerator on 2019/06/10.
 */
public interface LabelService extends Service<Label> {

    List<Label> select(Label label);
}
