package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "video_contents")
public class VideoContents {
    /**
     * 视频目录表
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 视频目录标题
     */
    private String name;

    /**
     * 视频目录的视频链接
     */
    private String url;

    /**
     * 视频章节id
     */
    @Column(name = "chapter_id")
    private Integer chapterId;

    /**
     * 排序
     */
    private Integer sort;

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
    private String img;

    /**
     * 获取视频目录表
     *
     * @return id - 视频目录表
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置视频目录表
     *
     * @param id 视频目录表
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取视频目录标题
     *
     * @return name - 视频目录标题
     */
    public String getName() {
        return name;
    }

    /**
     * 设置视频目录标题
     *
     * @param name 视频目录标题
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取视频目录的视频链接
     *
     * @return url - 视频目录的视频链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置视频目录的视频链接
     *
     * @param url 视频目录的视频链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}