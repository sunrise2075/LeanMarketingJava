package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Role {
    /**
     * 启用状态
     */
    public static final Integer IS_USE = 1;
    /**
     * 禁用状态
     */
    public static final Integer NO_USE = 2;
    /**
     * 角色id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 权限集合
     */
    private String codes;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色状态(1,启用,2禁用)
     */
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
    /**
     * 角色表的唯一标识
     */
    private Integer uniqueCode;

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    /**
     * 获取角色id
     *
     * @return id - 角色id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置角色id
     *
     * @param id 角色id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取角色状态(1,启用,2禁用)
     *
     * @return state - 角色状态(1,启用,2禁用)
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置角色状态(1,启用,2禁用)
     *
     * @param state 角色状态(1,启用,2禁用)
     */
    public void setState(Integer state) {
        this.state = state;
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

    public Integer getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(Integer uniqueCode) {
        this.uniqueCode = uniqueCode;
    }
}