package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Employee {
    /**
     * 禁用
     */
    public static final Integer NO_USE = 2;
    /**
     * 启用
     */
    public static final Integer IS_USE = 1;
    /**
     * 员工总监身份
     */
    public static final Integer EMPLOYEE_DIRECTOR_IDENTITY = 1;
    /**
     * 员工职员身份
     */
    public static final Integer EMPLOYEE_CLERK_IDENTITY = 2;
    /**
     * 员工账号id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id(企业身份)
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 员工名称
     */
    private String name;

    /**
     * 身份(1总监2职员)
     */
    private Integer identity;

    /**
     * 员工头像
     */
    @Column(name = "head_portrait")
    private String headPortrait;

    /**
     * 手机号
     */
    private String phone;

    private Integer state;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取员工账号id
     *
     * @return id - 员工账号id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置员工账号id
     *
     * @param id 员工账号id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id(企业身份)
     *
     * @return user_id - 用户id(企业身份)
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id(企业身份)
     *
     * @param userId 用户id(企业身份)
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取员工名称
     *
     * @return name - 员工名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置员工名称
     *
     * @param name 员工名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取身份(1总监2职员)
     *
     * @return identity - 身份(1总监2职员)
     */
    public Integer getIdentity() {
        return identity;
    }

    /**
     * 设置身份(1总监2职员)
     *
     * @param identity 身份(1总监2职员)
     */
    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    /**
     * 获取员工头像
     *
     * @return head_portrait - 员工头像
     */
    public String getHeadPortrait() {
        return headPortrait;
    }

    /**
     * 设置员工头像
     *
     * @param headPortrait 员工头像
     */
    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}