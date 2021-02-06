package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "video_order")
public class VideoOrder {

    /**
     * 视频订单未支付状态
     */
    public static final Integer NOPAY = 1;
    /**
     * 视频订单已支付状态
     */
    public static final Integer ISPAY = 2;
    /**
     * 视频订单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 视频id
     */
    @Column(name = "video_id")
    private Integer videoId;

    /**
     * 视频标题
     */
    @Column(name = "video_title")
    private String videoTitle;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户的唯一标识
     */
    private String openid;

    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 支付方式
     */
    private String paymemt;

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
     * 支付编码
     */
    @Column(name = "pay_number")
    private String payNumber;

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
    /**
     * 地址(为了表明这个订单是属于那个代理商的)
     */
    private String address;

    /**
     * 获取视频订单id
     *
     * @return id - 视频订单id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置视频订单id
     *
     * @param id 视频订单id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取视频id
     *
     * @return video_id - 视频id
     */
    public Integer getVideoId() {
        return videoId;
    }

    /**
     * 设置视频id
     *
     * @param videoId 视频id
     */
    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    /**
     * 获取视频标题
     *
     * @return video_title - 视频标题
     */
    public String getVideoTitle() {
        return videoTitle;
    }

    /**
     * 设置视频标题
     *
     * @param videoTitle 视频标题
     */
    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
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
     * @return paymemt - 支付方式
     */
    public String getPaymemt() {
        return paymemt;
    }

    /**
     * 设置支付方式
     *
     * @param paymemt 支付方式
     */
    public void setPaymemt(String paymemt) {
        this.paymemt = paymemt;
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
     * 获取支付编码
     *
     * @return pay_number - 支付编码
     */
    public String getPayNumber() {
        return payNumber;
    }

    /**
     * 设置支付编码
     *
     * @param payNumber 支付编码
     */
    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}