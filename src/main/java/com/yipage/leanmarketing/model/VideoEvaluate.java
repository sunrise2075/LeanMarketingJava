package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "video_evaluate")
public class VideoEvaluate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 视频id
     */
    @Column(name = "video_id")
    private Integer videoId;

    /**
     * 描述
     */
    private String description;

    @Column(name = "user_id")
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户头像
     */
    private String userHeadPortrait;
    /**
     * 评价图片
     */
    @Column(name = "evaluate_img")
    private String evaluateImg;
    /**
     * 星的数量
     */
    private Integer starNum;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadPortrait() {
        return userHeadPortrait;
    }

    public void setUserHeadPortrait(String userHeadPortrait) {
        this.userHeadPortrait = userHeadPortrait;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
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
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取评价图片
     *
     * @return evaluate_img - 评价图片
     */
    public String getEvaluateImg() {
        return evaluateImg;
    }

    /**
     * 设置评价图片
     *
     * @param evaluateImg 评价图片
     */
    public void setEvaluateImg(String evaluateImg) {
        this.evaluateImg = evaluateImg;
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
}