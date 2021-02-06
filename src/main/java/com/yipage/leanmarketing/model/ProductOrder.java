package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "product_order")
public class ProductOrder {
    /**
     * 商品订单未支付状态(待付款)
     */
    public static final Integer NOPAY = 1;
    /**
     * 商品订单待发货
     */
    public static final Integer NEEDSEND = 2;
    /**
     * 待收货)
     */
    public static final Integer NEEDRECEIVED = 3;
    /**
     * 待评价
     */
    public static final Integer NOEVALUATE = 4;
    /**
     * 成功
     */
    public static final Integer ISSUCCESS = 5;
    /**
     * 商品订单已取消状态
     */
    public static final Integer ISCANCEL = 6;
    /**
     * 订单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Integer productId;
    /**
     * 订单商品名称
     */
    @Column(name = "product_name")
    private String productName;
    /**
     * 商品数量
     */
    private Integer productNum;
    /**
     * 用户id
     */
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
     * 支付编号
     */
    @Column(name = "pay_number")
    private String payNumber;
    /**
     * 支付状态(1待付款，2待收货，3待评价，4成功 5 已取消)
     */
    @Column(name = "pay_state")
    private Integer payState;
    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;
    /**
     * 订单价格
     */
    @Column(name = "pay_money")
    private BigDecimal payMoney;
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
     * 物流公司
     */
    private String logisticsCompany;
    /**
     * 物流单号
     */
    private String courierNumber;
    /**
     * 发货时间
     */
    private Date sendTime;
    /**
     * 评价时间
     */
    private Date evaluateTime;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "receiver_phone")
    private String receiverPhone;
    @Column(name = "receiver_address")
    private String receiverAddress;
    @Column(name = "receive_time")
    private Date receiveTime;
    /**
     * 自动收货时间
     */
    @Column(name = "auto_receive_time")
    private Date autoReceiveTime;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    /**
     * 获取订单id
     *
     * @return id - 订单id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置订单id
     *
     * @param id 订单id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取订单商品名称
     *
     * @return product_name - 订单商品名称
     */
    public String getProductName() {
        return productName;
    }

    /**
     * 设置订单商品名称
     *
     * @param productName 订单商品名称
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
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
     * 获取支付编号
     *
     * @return pay_number - 支付编号
     */
    public String getPayNumber() {
        return payNumber;
    }

    /**
     * 设置支付编号
     *
     * @param payNumber 支付编号
     */
    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    /**
     * 获取支付状态(1未支付，2已支付，3已发货，4已收货)
     *
     * @return pay_state - 支付状态(1未支付，2已支付，3已发货，4已收货)
     */
    public Integer getPayState() {
        return payState;
    }

    /**
     * 设置支付状态(1未支付，2已支付，3已发货，4已收货)
     *
     * @param payState 支付状态(1未支付，2已支付，3已发货，4已收货)
     */
    public void setPayState(Integer payState) {
        this.payState = payState;
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
     * 获取订单价格
     *
     * @return pay_money - 订单价格
     */
    public BigDecimal getPayMoney() {
        return payMoney;
    }

    /**
     * 设置订单价格
     *
     * @param payMoney 订单价格
     */
    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
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

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getEvaluateTime() {
        return evaluateTime;
    }

    public void setEvaluateTime(Date evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getAutoReceiveTime() {
        return autoReceiveTime;
    }

    public void setAutoReceiveTime(Date autoReceiveTime) {
        this.autoReceiveTime = autoReceiveTime;
    }
}