package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lean_marketing")
public class LeanMarketing {
    /**
     * leanMarketing 的id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 封面图
     */
    @Column(name = "cover_img")
    private String coverImg;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取leanMarketing 的id
     *
     * @return id - leanMarketing 的id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置leanMarketing 的id
     *
     * @param id leanMarketing 的id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取简介
     *
     * @return introduction - 简介
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置简介
     *
     * @param introduction 简介
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取封面图
     *
     * @return cover_img - 封面图
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * 设置封面图
     *
     * @param coverImg 封面图
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
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
}