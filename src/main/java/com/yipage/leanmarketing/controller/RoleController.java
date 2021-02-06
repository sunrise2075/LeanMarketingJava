package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Authority;
import com.yipage.leanmarketing.model.Role;
import com.yipage.leanmarketing.service.AuthorityService;
import com.yipage.leanmarketing.service.RoleService;
import com.yipage.leanmarketing.utils.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private AuthorityService authorityService;

    @PostMapping("add")
    public Result add(Role role) {
        Role role1 = roleService.findBy("uniqueCode", role.getUniqueCode());
        if (role1 != null) {
            return ResultGenerator.genFailResult("唯一标识重复,请重新添加");
        }
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        roleService.save(role);
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        roleService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("update")
    public Result update(Role role) {

        Role role1 = roleService.findById(role.getId());
        if (role1.getUniqueCode() != role.getUniqueCode()) { //不相同

            Role role2 = roleService.findBy("uniqueCode", role.getUniqueCode());
            if (role2 != null) {
                return ResultGenerator.genFailResult("唯一标识重复,请重新添加");
            }
        }
        role.setUpdateTime(new Date());
        roleService.update(role);
        return ResultGenerator.genSuccessResult();

    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Role role = roleService.findById(id);
        return ResultGenerator.genSuccessResult(role);
    }

    @GetMapping("list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "0") Integer limit) {
        String orderBy = "update_time desc";
        PageHelper.startPage(page, limit, orderBy);
        List<Role> list = roleService.findAll();
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Role role : list) {
                Map<String, Object> roleMap = new HashMap<>();
                roleMap.put("id", role.getId());
                roleMap.put("name", role.getName());
                roleMap.put("state", role.getState());
                roleMap.put("codes", role.getCodes());
                roleMap.put("uniqueCode", role.getUniqueCode());
                roleMap.put("createTime", role.getCreateTime());
                roleMap.put("updateTime", role.getUpdateTime());
                String codes = role.getCodes();
                if (StringUtils.isNotEmpty(codes)) {
                    String[] codeStr = codes.split(",");
                    String authorityName = "";
                    for (String code : codeStr) {
                        Authority authority = authorityService.findById(Integer.parseInt(code));
                        if (authority != null) {
                            authorityName += authority.getName() + ",";
                        }
                    }
                    if (authorityName.length() > 0) {
                        roleMap.put("authority", authorityName.substring(0, authorityName.length() - 1));
                    }
                }
                list2.add(roleMap);
            }
        }
        PageInfo pageInfo = new PageInfo(list2);
        return MapUtil.PageResult(pageInfo);
    }

    @GetMapping("listAll")
    public Result listAll() {
        Role role = new Role();
        role.setState(Role.IS_USE);
        List<Role> list = roleService.select(role);
        return ResultGenerator.genSuccessResult(list);
    }
}
