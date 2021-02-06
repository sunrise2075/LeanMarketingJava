package com.yipage.leanmarketing.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Authority {
    /**
     * 权限id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 资源代号
     */
    private Integer code;

    /**
     * 获取权限id
     *
     * @return id - 权限id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置权限id
     *
     * @param id 权限id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取资源代号
     *
     * @return code - 资源代号
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置资源代号
     *
     * @param code 资源代号
     */
    public void setCode(Integer code) {
        this.code = code;
    }

}


