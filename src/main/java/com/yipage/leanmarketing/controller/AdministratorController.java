package com.yipage.leanmarketing.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yipage.leanmarketing.core.Result;
import com.yipage.leanmarketing.core.ResultGenerator;
import com.yipage.leanmarketing.model.Administrator;
import com.yipage.leanmarketing.model.Role;
import com.yipage.leanmarketing.service.AdministratorService;
import com.yipage.leanmarketing.service.RoleService;
import com.yipage.leanmarketing.utils.MapUtil;
import com.yipage.leanmarketing.utils.TokenManagerUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by CodeGenerator on 2018/12/10.
 */
@RequestMapping("/administrator")
@RestController
public class AdministratorController {

    @Resource
    private AdministratorService administratorService;

    @Resource
    private RoleService roleService;

    @Resource
    private HttpServletRequest request;


    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @param session  session对象
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public boolean login(@RequestParam(value = "username") String username,
                         @RequestParam(value = "password") String password,
                         HttpSession session) {

        Administrator administrator = administratorService.getAdministratorByUsernameAndPassword(username, password);
        if (administrator != null) {
            // 将user存入session中
            //session.setMaxInactiveInterval(30 * 60); // 设置session有效期为30分钟（单位：秒）
            session.setAttribute(Administrator.LOGIN_ADMIN_SESSION, administrator);
            TokenManagerUtil.createToken(administrator.getId());
            return true;
        }
        return false;
    }

    /**
     * 退出登录
     *
     * @return
     */
    @RequestMapping(value = "/login_out")
    public boolean loginOut(HttpSession session) {
        // 移除session中的user对象
        session.removeAttribute(Administrator.LOGIN_ADMIN_SESSION);
        return true;
    }

    @PostMapping(value = "/add")
    public Result add(Administrator administrator) {


        Integer result = administratorService.save(administrator);
        if (result == 0) {
            //说明不存在相同的用户名
            return ResultGenerator.genSuccessResult(result);
        } else if (result == 1) {
            return ResultGenerator.genFailResult("存在相同的用户名");
        } else {
            return ResultGenerator.genFailResult("该手机号已存在");
        }
    }


    @PostMapping("/update")
    public Result update(Administrator administrator) {
        administrator.setUpdateTime(new Date());
        Integer result = administratorService.update(administrator);
        if (result == 0) {
            return ResultGenerator.genSuccessResult();
        } else if (result == 1) {
            return ResultGenerator.genFailResult("存在相同的用户名");
        } else if (result == 2) {
            return ResultGenerator.genFailResult("此管理员不存在");
        } else {
            return ResultGenerator.genFailResult("此手机号已存在");
        }
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Administrator administrator = administratorService.findById(id);
        return ResultGenerator.genSuccessResult(administrator);
    }

    @GetMapping("/list")
    public Map list(@RequestParam(defaultValue = "1") Integer page,
                    @RequestParam(defaultValue = "10") Integer limit,
                    @RequestParam(value = "username", required = false) String username,
                    @RequestParam(value = "phone", required = false) String phone,
                    @RequestParam(value = "state", required = false) Integer state) {

        Condition condition = new Condition(Administrator.class);
        Example.Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotEmpty(username)) {
            criteria.andCondition("username like '%" + username + "%'");
        }
        if (StringUtils.isNotEmpty(phone)) {
            criteria.andCondition("phone like '%" + phone + "%'");
        }
        if (state != null) {
            criteria.andEqualTo("state", state);
        }
        String orderBy = "update_time desc";
        PageHelper.startPage(page, limit, orderBy);
        List<Administrator> list = administratorService.findByCondition(condition);
        List<Map<String, Object>> list2 = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Administrator administrator : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("username", administrator.getUsername());
                map.put("id", administrator.getId());
                map.put("roleId", administrator.getRoleId());
                map.put("createTime", administrator.getCreateTime());
                map.put("headPortrait", administrator.getHeadPortrait());
                map.put("mail", administrator.getMail());
                map.put("phone", administrator.getPhone());
                map.put("state", administrator.getState());
                Role role = roleService.findById(administrator.getRoleId());
                if (role != null) {
                    map.put("roleName", role.getName());
                }
                list2.add(map);
            }
        }
        PageInfo pageInfo = new PageInfo(list2);
        return MapUtil.PageResult(pageInfo);
    }

    /**
     * 批量删除管理员
     *
     * @param ids
     * @return
     */
    @GetMapping("deletes/{ids}")
    public Result deletes(@PathVariable String ids) {
        administratorService.deleteByIds(ids);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 权限管理
     */
    @PostMapping("isHaveAuthority")
    public Result isHaveAuthority() {

        Administrator administrator = (Administrator) request.getSession().getAttribute(Administrator.LOGIN_ADMIN_SESSION);

        if (administrator == null) {
            return ResultGenerator.genFailResult("管理员信息找不到");
        }
        if (administrator.getRoleId() != null) {

            Role role = roleService.findById(administrator.getRoleId());

            if (role == null) {
                return ResultGenerator.genFailResult("管理员没有角色信息");
            }
            return ResultGenerator.genSuccessResult(role);

        }
        return ResultGenerator.genFailResult("系统发生错误");
    }

    @GetMapping("/test")
    public void test() {


    }

}
