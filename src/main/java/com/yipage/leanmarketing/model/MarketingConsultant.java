package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "marketing_consultant")
public class MarketingConsultant {
    /**
     * 营销顾问
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

    @Column(name = "cover_img")
    private String coverImg;

    /**
     * 标题
     */
    private String title;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 内容(富文本)
     */
    private String content;

    /**
     * 获取营销顾问
     *
     * @return id - 营销顾问
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置营销顾问
     *
     * @param id 营销顾问
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
     * @return cover_img
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * @param coverImg
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

    /**
     * 获取内容(富文本)
     *
     * @return content - 内容(富文本)
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容(富文本)
     *
     * @param content 内容(富文本)
     */
    public void setContent(String content) {
        this.content = content;
    }
}