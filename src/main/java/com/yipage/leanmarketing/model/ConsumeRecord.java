package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "consume_record")
public class ConsumeRecord {

    /**
     * 视频消费类型
     */
    public static final Integer CONSUME_TYPE_VIDEO = 1;
    /**
     * 文库消费类型
     */
    public static final Integer CONSUME_TYPE_LIBARAT = 2;
    /**
     * 考试消费类型
     */
    public static final Integer CONSUME_TYPE_EXAM = 3;
    /**
     * 会员充值类型
     */
    public static final Integer CONSUME_TYPE_RECHARGE = 4;

    /**
     * 消费记录id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户唯一标识
     */
    private String openid;


    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_grade")
    private Integer userGrade;

    /**
     * 用户头像
     */
    private String userHeadPortrait;
    /**
     * 订单号
     */
    @Column(name = "order_number")
    private String orderNumber;
    /**
     * 用户所在地
     */
    private String address;
    /**
     * 消费类型(1视频消费2文库消费3考试消费4会员充值)
     */
    private Integer type;
    /**
     * 支付金额
     */
    @Column(name = "pay_money")
    private BigDecimal payMoney;
    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    private Date payTime;
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
    private Integer superiorId;

    public String getUserHeadPortrait() {
        return userHeadPortrait;
    }

    public void setUserHeadPortrait(String userHeadPortrait) {
        this.userHeadPortrait = userHeadPortrait;
    }

    /**
     * 获取消费记录
     *
     * @return id - 消费记录
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置消费记录
     *
     * @param id 消费记录
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
     * @return user_grade
     */
    public Integer getUserGrade() {
        return userGrade;
    }

    /**
     * @param userGrade
     */
    public void setUserGrade(Integer userGrade) {
        this.userGrade = userGrade;
    }

    /**
     * 获取订单号
     *
     * @return order_number - 订单号
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置订单号
     *
     * @param orderNumber 订单号
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 获取用户所在地
     *
     * @return address - 用户所在地
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置用户所在地
     *
     * @param address 用户所在地
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取消费类型((1视频消费2文库消费3考试消费4会员充值)
     *
     * @return type - 消费类型(1视频消费2文库消费3考试消费4会员充值)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置消费类型(1视频消费2文库消费3考试消费4会员充值)
     *
     * @param type 消费类型(1视频消费2文库消费3考试消费4会员充值)
     */
    public void setType(Integer type) {
        this.type = type;
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

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }
}