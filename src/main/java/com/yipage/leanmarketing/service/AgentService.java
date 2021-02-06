package com.yipage.leanmarketing.service;

import com.yipage.leanmarketing.core.Service;
import com.yipage.leanmarketing.model.Agent;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
public interface AgentService extends Service<Agent> {

    List<Agent> select(Agent agent);
}
