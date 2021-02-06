package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.ExamOrder;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/13.
 */
public interface ExamOrderService extends Service<ExamOrder> {

    List<ExamOrder> select(ExamOrder examOrder);

    void afterPaySucess(ExamOrder examOrder);
}
