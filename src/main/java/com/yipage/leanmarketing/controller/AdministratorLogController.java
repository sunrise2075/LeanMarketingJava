package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.AdministratorLog;
import com.yipage.leanmarketing.service.AdministratorLogService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@RequestMapping("/administrator/log")
public class AdministratorLogController {
    @Resource
    private AdministratorLogService administratorLogService;

    @PostMapping
    public Result add(@RequestBody AdministratorLog administratorLog) {
        administratorLog.setUpdateTime(new Date());
        administratorLogService.save(administratorLog);
        return ResultGenerator.genSuccessResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        administratorLogService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PutMapping
    public Result update(@RequestBody AdministratorLog administratorLog) {
        administratorLog.setUpdateTime(new Date());
        administratorLogService.update(administratorLog);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        AdministratorLog administratorLog = administratorLogService.findById(id);
        return ResultGenerator.genSuccessResult(administratorLog);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "operateType", required = false) Integer operateType,
                    @RequestParam(value = "operateName", required = false) String operateName,
                    @RequestParam(value = "startTime", required = false) String startTime,
                    @RequestParam(value = "endTime", required = false) String endTime) {
        Condition condition = new Condition(AdministratorLog.class);
        Example.Criteria criteria = condition.createCriteria();

        if (operateType != null) {
            criteria.andCondition("operate_type = '" + operateType + "'");
        }
        if (StringUtils.isNotEmpty(operateName)) {
            criteria.andCondition("operateName = '" + operateName + "'");
        }
        if (StringUtils.isNotEmpty(startTime)) {
            criteria.andCondition("update_time > '" + startTime + "'");
        }
        if (StringUtils.isNotEmpty(endTime)) {

            criteria.andCondition("update_time < '" + endTime + "'");
        }
        PageHelper.startPage(page, limit);
        List<AdministratorLog> list = administratorLogService.findByCondition(condition);
        PageInfo pageInfo = new PageInfo(list);
        return MapUtil.PageResult(pageInfo);
    }
}
