package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "watch_record")
public class WatchRecord {
    /**
     * 观看记录id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 观看视频id
     */
    @Column(name = "video_id")
    private Integer videoId;

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
    @Column(name = "video_name")
    private String videoName;
    @Column(name = "user_name")
    private String userName;
    /**
     * 视频内容id
     */
    private Integer contentId;
    /**
     * 观看时长
     */
    private String duration;
    /**
     * 视频内容总时长
     */
    private String sumDuration;
    private String img;
    private Integer isFree;
    private BigDecimal price;

    /**
     * 获取观看记录id
     *
     * @return id - 观看记录id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置观看记录id
     *
     * @param id 观看记录id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取观看视频id
     *
     * @return video_id - 观看视频id
     */
    public Integer getVideoId() {
        return videoId;
    }

    /**
     * 设置观看视频id
     *
     * @param videoId 观看视频id
     */
    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
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

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(String sumDuration) {
        this.sumDuration = sumDuration;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}