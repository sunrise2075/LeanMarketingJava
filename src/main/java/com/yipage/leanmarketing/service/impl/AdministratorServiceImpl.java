package com.yipage.leanmarketing.service.impl;

import com.yipage.leanmarketing.core.AbstractService;
import com.yipage.leanmarketing.mapper.AdministratorLogMapper;
import com.yipage.leanmarketing.mapper.AdministratorMapper;
import com.yipage.leanmarketing.mapper.RoleMapper;
import com.yipage.leanmarketing.model.Administrator;
import com.yipage.leanmarketing.model.AdministratorLog;
import com.yipage.leanmarketing.model.Role;
import com.yipage.leanmarketing.service.AdministratorService;
import com.yipage.leanmarketing.utils.AddAdministratorLogUtil;
import com.yipage.leanmarketing.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/12/10.
 */
@Service
@Transactional
public class AdministratorServiceImpl extends AbstractService<Administrator> implements AdministratorService {
    @Resource
    private AdministratorMapper administratorMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private AdministratorLogMapper administratorLogMapper;
    @Resource
    private HttpServletRequest request;

    @Override
    public Administrator getAdministratorByUsernameAndPassword(String username, String password) {

        Administrator administrator = administratorMapper.getAdministratorByUsernameAndPassword(username, MD5Util.MD5(password));
        return administrator;
    }

    @Override
    public List<Administrator> select(Administrator administrator) {
        return administratorMapper.select(administrator);
    }

    @Override
    public Integer update2(Administrator administrator) {
        return administratorMapper.updateByPrimaryKeySelective(administrator);
    }

    @Override
    public Integer save(Administrator model) {
        Administrator admin = (Administrator) request.getSession().getAttribute(Administrator.LOGIN_ADMIN_SESSION);

        //手机号不能重复
        Administrator a = new Administrator();
        a.setPhone(model.getPhone());
        Administrator b = administratorMapper.selectOne(a);
        if (b != null) {
            return 2;
        }
        //默认密码为123456
        //查找是否已经存在的用户名
        //查找用户角色
        Role role = roleMapper.selectByPrimaryKey(model.getRoleId());
        if (role != null) {
            model.setRoleName(role.getName());
            model.setRoleCode(role.getUniqueCode());
        }
        Administrator administrator = new Administrator();
        administrator.setUsername(model.getUsername());
        List<Administrator> administratorList = administratorMapper.select(administrator);
        if (CollectionUtils.isEmpty(administratorList)) {
            model.setCreateTime(new Date());
            model.setUpdateTime(new Date());
            model.setRoleCode(Administrator.ADMINISTRATOR_ROLE);
            model.setPassword(MD5Util.MD5("123456"));
            administratorMapper.insertSelective(model);
            //添加一个日志记录
            String content = "添加后台管理员";
            AdministratorLog administratorLog = AddAdministratorLogUtil.add(content, admin.getId(),
                    admin.getUsername(), request.getRemoteAddr(), AdministratorLog.OPERATE_TYPE_ADD);
            administratorLogMapper.insertSelective(administratorLog);
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public Integer update(Administrator model) {

        //待优化
        Administrator a = new Administrator();
        a.setId(model.getId());

        Administrator originAdministrator = administratorMapper.selectOne(a);
        if (originAdministrator != null) {
            //查找出原来的手机号与提交上来的手机号相比较
            if (!StringUtils.isEmpty(originAdministrator.getPhone())) {
                if (!originAdministrator.getPhone().equals(model.getPhone())) {
                    Administrator c = new Administrator();
                    c.setPhone(model.getPhone());
                    Administrator d = administratorMapper.selectOne(c);
                    if (d != null) {
                        return 3;
                    }
                }
            }
            //查找出原来的用户名与提交上来的用户名相比较
            if (!StringUtils.isEmpty(originAdministrator.getUsername())) {
                if (!originAdministrator.getUsername().equals(model.getUsername())) {
                    //不相同则查找是否已经存在的用户名
                    Administrator administrator = new Administrator();
                    administrator.setUsername(model.getUsername());
                    List<Administrator> administratorList = administratorMapper.select(administrator);
                    if (CollectionUtils.isEmpty(administratorList)) {
                        //如果不存在就更新
                        if (!StringUtils.isEmpty(model.getPassword())) {
                            model.setPassword(MD5Util.MD5(model.getPassword()));
                        } else {
                            model.setPassword(null);
                        }
                        //将session更新
                        request.getSession().setAttribute(Administrator.LOGIN_ADMIN_SESSION, model);
                        super.update(model);
                        return 0;
                    } else {
                        return 1;
                    }
                } else {
                    //原来的用户名与提交上来的用户名相同直接更新
                    if (!StringUtils.isEmpty(model.getPassword())) {
                        model.setPassword(MD5Util.MD5(model.getPassword()));
                    } else {
                        model.setPassword(null);
                    }
                    //将session更新
                    request.getSession().setAttribute(Administrator.LOGIN_ADMIN_SESSION, model);
                    super.update(model);
                    return 0;
                }
            } else {
                //直接修改
                if (!StringUtils.isEmpty(model.getPassword())) {
                    model.setPassword(MD5Util.MD5(model.getPassword()));
                } else {
                    model.setPassword(null);
                }
                //将session更新
                request.getSession().setAttribute(Administrator.LOGIN_ADMIN_SESSION, model);
                super.update(model);
                return 0;
            }
        } else {
            //找不到此管理员
            return 2;
        }
    }


}
