package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lesson_collage_record")
public class LessonCollageRecord {

    public static final int IS_LEADER = 1;
    public static final int NOT_LEADER = 2;
    /**
     * 拼课记录
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 拼课id
     */
    @Column(name = "lesson_id")
    private Integer lessonId;

    /**
     * 拼课名称
     */
    @Column(name = "lesson_name")
    private String lessonName;

    /**
     * 拼课标题
     */
    @Column(name = "lesson_title")
    private String lessonTitle;

    /**
     * 拼课价格
     */
    private BigDecimal price;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户头像
     */
    @Column(name = "user_img")
    private String userImg;

    /**
     * 用户头像
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 开课时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

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
     * 是否是团长
     */
    @Column(name = "is_leader")
    private Integer isLeader;
    /**
     * 团长id
     */
    @Column(name = "leader_id")
    private Integer leaderId;
    /**
     * 还差人数
     */
    @Column(name = "miss_num")
    private Integer missNum;
    /**
     * 参与人数
     */
    @Column(name = "join_num")
    private Integer joinNum;
    @Column(name = "record_num")
    private String recordNum;
    /**
     * 上课地址
     */
    private String address;
    /**
     * 拼课结束时间
     */
    @Column(name = "end_time")
    private Date endTime;
    /**
     * 拼团状态(1拼团中,2拼团成功 3 拼团失败)
     */
    private Integer status;
    /**
     * 订单号
     */
    @Column(name = "order_num")
    private String orderNum;
    /**
     * 假删(1删除0未删除)
     */
    private Integer isDelete;
    @Column(name = "lesson_img")
    private String lessonImg;
    @Column(name = "is_need_provide_address")
    private Integer isNeedProvideAddress;
    private String phone;
    private Integer isProvideAddress;

    /**
     * 获取拼课记录
     *
     * @return id - 拼课记录
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置拼课记录
     *
     * @param id 拼课记录
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取拼课id
     *
     * @return lesson_id - 拼课id
     */
    public Integer getLessonId() {
        return lessonId;
    }

    /**
     * 设置拼课id
     *
     * @param lessonId 拼课id
     */
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    /**
     * 获取拼课名称
     *
     * @return lesson_name - 拼课名称
     */
    public String getLessonName() {
        return lessonName;
    }

    /**
     * 设置拼课名称
     *
     * @param lessonName 拼课名称
     */
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    /**
     * 获取拼课标题
     *
     * @return lesson_title - 拼课标题
     */
    public String getLessonTitle() {
        return lessonTitle;
    }

    /**
     * 设置拼课标题
     *
     * @param lessonTitle 拼课标题
     */
    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    /**
     * 获取拼课价格
     *
     * @return price - 拼课价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置拼课价格
     *
     * @param price 拼课价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * 获取用户头像
     *
     * @return user_img - 用户头像
     */
    public String getUserImg() {
        return userImg;
    }

    /**
     * 设置用户头像
     *
     * @param userImg 用户头像
     */
    public void setUserImg(String userImg) {
        this.userImg = userImg;
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

    public Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }

    public Integer getMissNum() {
        return missNum;
    }

    public void setMissNum(Integer missNum) {
        this.missNum = missNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getLessonImg() {
        return lessonImg;
    }

    public void setLessonImg(String lessonImg) {
        this.lessonImg = lessonImg;
    }

    public Integer getIsNeedProvideAddress() {
        return isNeedProvideAddress;
    }

    public void setIsNeedProvideAddress(Integer isNeedProvideAddress) {
        this.isNeedProvideAddress = isNeedProvideAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getIsProvideAddress() {
        return isProvideAddress;
    }

    public void setIsProvideAddress(Integer isProvideAddress) {
        this.isProvideAddress = isProvideAddress;
    }
}