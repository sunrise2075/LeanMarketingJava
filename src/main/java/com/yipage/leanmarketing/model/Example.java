package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Example {
    /**
     * 案例
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标题
     */
    private String name;

    @Column(name = "cover_img")
    private String coverImg;

    private String introduction;

    /**
     * 标题
     */
    private String title;

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
     * 内容(富文本)
     */
    private String content;

    /**
     * 获取案例
     *
     * @return id - 案例
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置案例
     *
     * @param id 案例
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取标题
     *
     * @return name - 标题
     */
    public String getName() {
        return name;
    }

    /**
     * 设置标题
     *
     * @param name 标题
     */
    public void setName(String name) {
        this.name = name;
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
     * @return introduction
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * @param introduction
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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