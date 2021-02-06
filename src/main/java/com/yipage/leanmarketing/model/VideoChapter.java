package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "video_chapter")
public class VideoChapter {

    /**
     * 显示
     */
    public static final Integer IS_SHOW = 1;
    /**
     * 隐藏
     */
    public static final Integer IS_HIDE = 2;

    /**
     * 视频章节id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 视频说明
     */
    private String name;

    /**
     * 视频章节id
     */
    @Column(name = "video_id")
    private Integer videoId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否隐藏
     */
    private Integer isHide;

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
    private String introduction;

    /**
     * 获取视频章节id
     *
     * @return id - 视频章节id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置视频章节id
     *
     * @param id 视频章节id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取视频说明
     *
     * @return name - 视频说明
     */
    public String getName() {
        return name;
    }

    /**
     * 设置视频说明
     *
     * @param name 视频说明
     */
    public void setName(String name) {
        this.name = name;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}