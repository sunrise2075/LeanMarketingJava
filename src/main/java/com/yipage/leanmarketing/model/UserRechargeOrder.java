package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_recharge_order")
public class UserRechargeOrder {

    /**
     * 订单未支付状态
     */
    public static final Integer NOPAY = 1;
    /**
     * 订单已支付状态
     */
    public static final Integer ISPAY = 2;
    /**
     * 会员充值订单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 支付方式
     */
    private String payment;

    /**
     * 支付状态(1未支付2已支付)
     */
    @Column(name = "pay_state")
    private Integer payState;

    /**
     * 支付金额
     */
    @Column(name = "pay_money")
    private BigDecimal payMoney;

    /**
     * 支付订单号
     */
    @Column(name = "pay_number")
    private String payNumber;

    /**
     * 地址
     */
    private String address;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    private String openid;
    /**
     * 权益id
     */
    private Integer benfitId;
    /**
     * 权益名称
     */
    private String benfitName;
    /**
     * 一年还是半年
     */
    private Integer yearType;
    /**
     * 绑定的手机号
     */
    private String phone;
    /**
     * 代理人名称
     */
    private String name;
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 获取会员充值订单id
     *
     * @return id - 会员充值订单id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置会员充值订单id
     *
     * @param id 会员充值订单id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名称
     *
     * @return user_name - 用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     *
     * @param userName 用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取支付方式
     *
     * @return payment - 支付方式
     */
    public String getPayment() {
        return payment;
    }

    /**
     * 设置支付方式
     *
     * @param payment 支付方式
     */
    public void setPayment(String payment) {
        this.payment = payment;
    }

    /**
     * 获取支付状态(1未支付2已支付)
     *
     * @return pay_state - 支付状态(1未支付2已支付)
     */
    public Integer getPayState() {
        return payState;
    }

    /**
     * 设置支付状态(1未支付2已支付)
     *
     * @param payState 支付状态(1未支付2已支付)
     */
    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    /**
     * 获取支付金额
     *
     * @return pay_money - 支付金额
     */
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    /**
     * 设置支付金额
     *
     * @param payMoney 支付金额
     */
    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    /**
     * 获取支付订单号
     *
     * @return pay_number - 支付订单号
     */
    public String getPayNumber() {
        return payNumber;
    }

    /**
     * 设置支付订单号
     *
     * @param payNumber 支付订单号
     */
    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
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

    public Integer getBenfitId() {
        return benfitId;
    }

    public void setBenfitId(Integer benfitId) {
        this.benfitId = benfitId;
    }

    public String getBenfitName() {
        return benfitName;
    }

    public void setBenfitName(String benfitName) {
        this.benfitName = benfitName;
    }

    public Integer getYearType() {
        return yearType;
    }

    public void setYearType(Integer yearType) {
        this.yearType = yearType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}