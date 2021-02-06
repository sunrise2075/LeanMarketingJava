package com.yipage.leanmarketing.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Agent {
    /**
     * 代理商id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 代理商名称
     */
    private String name;

    /**
     * 代理商手机号
     */
    private String phone;

    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详情地址
     */
    @Column(name = "detailed_address")
    private String detailedAddress;

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
     * 过期时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expirationTime;

    /**
     * 获取代理商id
     *
     * @return id - 代理商id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置代理商id
     *
     * @param id 代理商id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取代理商名称
     *
     * @return name - 代理商名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置代理商名称
     *
     * @param name 代理商名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取代理商手机号
     *
     * @return phone - 代理商手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置代理商手机号
     *
     * @param phone 代理商手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取区
     *
     * @return area - 区
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置区
     *
     * @param area 区
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取详情地址
     *
     * @return detailed_address - 详情地址
     */
    public String getDetailedAddress() {
        return detailedAddress;
    }

    /**
     * 设置详情地址
     *
     * @param detailedAddress 详情地址
     */
    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
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

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
}