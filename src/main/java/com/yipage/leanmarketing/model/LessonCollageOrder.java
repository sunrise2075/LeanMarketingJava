package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lesson_collage_order")
public class LessonCollageOrder {

    /**
     * 订单未支付状态
     */
    public static final Integer NOPAY = 1;
    /**
     * 订单已支付状态
     */
    public static final Integer ISPAY = 2;
    /**
     * 拼课订单id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "lesson_id")
    private Integer lessonId;

    @Column(name = "lesson_img")
    private String lessonImg;

    @Column(name = "lesson_name")
    private String lessonName;

    @Column(name = "begin_time")
    private Date beginTime;

    @Column(name = "is_provide_address")
    private Integer isProvideAddress;

    private String address;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "update_time")
    private Date updateTime;
    private BigDecimal price;
    @Column(name = "pay_number")
    private String payNumber;
    @Column(name = "pay_state")
    private Integer payState;
    private String payment;
    private String openid;
    @Column(name = "user_img")
    private String userImg;
    /**
     * 是否是团长
     */
    @Column(name = "is_leader")
    private Integer isLeader;
    /**
     * 团长id
     */
    @Column(name = "leader_id")
    private Integer leaderId;
    @Column(name = "record_num")
    private String recordNum;

    /**
     * 获取拼课订单id
     *
     * @return id - 拼课订单id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置拼课订单id
     *
     * @param id 拼课订单id
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
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return user_phone
     */
    public String getUserPhone() {
        return userPhone;
    }

    /**
     * @param userPhone
     */
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    /**
     * @return lesson_id
     */
    public Integer getLessonId() {
        return lessonId;
    }

    /**
     * @param lessonId
     */
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * @return lesson_img
     */
    public String getLessonImg() {
        return lessonImg;
    }

    /**
     * @param lessonImg
     */
    public void setLessonImg(String lessonImg) {
        this.lessonImg = lessonImg;
    }

    /**
     * @return lesson_name
     */
    public String getLessonName() {
        return lessonName;
    }

    /**
     * @param lessonName
     */
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    /**
     * @return begin_time
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * @param beginTime
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * @return is_provide_address
     */
    public Integer getIsProvideAddress() {
        return isProvideAddress;
    }

    /**
     * @param isProvideAddress
     */
    public void setIsProvideAddress(Integer isProvideAddress) {
        this.isProvideAddress = isProvideAddress;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPayNumber() {
        return payNumber;
    }

    public void setPayNumber(String payNumber) {
        this.payNumber = payNumber;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public String getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }
}