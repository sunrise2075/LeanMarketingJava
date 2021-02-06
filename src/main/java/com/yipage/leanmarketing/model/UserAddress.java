package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_address")
public class UserAddress {

    /**
     * 是默认地址
     */
    public static final Integer IS_DEFAULT_ADDRESS = 1;
    /**
     * 不是默认地址
     */
    public static final Integer NO_DEFAULT_ADDRESS = 2;
    /**
     * 用户收货地址id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
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
     * 收货地址
     */
    private String address;
    /**
     * 是否默认地址(1是,2不是)
     */
    @Column(name = "is_default_address")
    private Integer isDefaultAddress;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 固定电话
     */
    @Column(name = "fixed_mobie")
    private String fixedMobie;
    /**
     * 收货人联系号码
     */
    private String phone;
    /**
     * 邮编
     */
    private String code;
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
    private String openid;
    /**
     * 额外的字段(pc要存起来的数据)
     */
    private String extra;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取用户收货地址id
     *
     * @return id - 用户收货地址id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户收货地址id
     *
     * @param id 用户收货地址id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取收货地址
     *
     * @return address - 收货地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置收货地址
     *
     * @param address 收货地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取是否默认地址(1是,2不是)
     *
     * @return is_default_address - 是否默认地址(1是,2不是)
     */
    public Integer getIsDefaultAddress() {
        return isDefaultAddress;
    }

    /**
     * 设置是否默认地址(1是,2不是)
     *
     * @param isDefaultAddress 是否默认地址(1是,2不是)
     */
    public void setIsDefaultAddress(Integer isDefaultAddress) {
        this.isDefaultAddress = isDefaultAddress;
    }

    /**
     * 获取收货人
     *
     * @return receiver - 收货人
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * 设置收货人
     *
     * @param receiver 收货人
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * 获取固定电话
     *
     * @return fixed_mobie - 固定电话
     */
    public String getFixedMobie() {
        return fixedMobie;
    }

    /**
     * 设置固定电话
     *
     * @param fixedMobie 固定电话
     */
    public void setFixedMobie(String fixedMobie) {
        this.fixedMobie = fixedMobie;
    }

    /**
     * 获取收货人联系号码
     *
     * @return phone - 收货人联系号码
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置收货人联系号码
     *
     * @param phone 收货人联系号码
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取邮编
     *
     * @return code - 邮编
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置邮编
     *
     * @param code 邮编
     */
    public void setCode(String code) {
        this.code = code;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}