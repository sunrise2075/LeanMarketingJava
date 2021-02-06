package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Agent;
import com.yipage.leanmarketing.service.AgentService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@RequestMapping("/agent")
public class AgentController {
    @Resource
    private AgentService agentService;

    @PostMapping("add")
    public Result add(Agent agent) {

        return dealResult(agentService.save(agent));
    }

    @PostMapping("update")
    public Result update(Agent agent) {

        return dealResult(agentService.update(agent));

    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Agent agent = agentService.findById(id);
        return ResultGenerator.genSuccessResult(agent);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "phone", required = false) String phone,
                    @RequestParam(value = "name", required = false) String name) {

        Condition condition = new Condition(Agent.class);
        Example.Criteria criteria = condition.createCriteria();
        if (!StringUtil.isEmpty(phone)) {
            criteria.andCondition("phone like '%" + phone + "%'");
        }
        if (!StringUtil.isEmpty(name)) {
            criteria.andCondition("name like '%" + name + "%'");
        }
        String orderBy = "create_time desc";
        PageHelper.startPage(page, limit, orderBy);
        List<Agent> list = agentService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);

        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        agentService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }


    private Result dealResult(Integer result) {

        if (result == 1) {
            return ResultGenerator.genSuccessResult();
        } else if (result == 2) {
            return ResultGenerator.genFailResult("此手机存在黑名单里面");
        } else if (result == 3) {
            return ResultGenerator.genFailResult("此手机存在子用户表中");
        } else if (result == 4) {
            return ResultGenerator.genFailResult("此手机存在员工管理中");
        } else if (result == 5) {
            return ResultGenerator.genFailResult("此手机存在代理商中");
        } else if (result == 6) {
            return ResultGenerator.genFailResult("此手机存在经销商中");
        } else if (result == 7) {
            return ResultGenerator.genFailResult("此区域已经被代理");
        } else {
            return ResultGenerator.genFailResult("此手机存在用户表中");
        }
    }
}
