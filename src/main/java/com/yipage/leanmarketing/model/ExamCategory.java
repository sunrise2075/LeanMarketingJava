package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "exam_category")
public class ExamCategory {

    /**
     * 隐藏
     */
    public static final Integer IS_HIDE = 2;
    /**
     * 显示
     */
    public static final Integer IS_SHOW = 1;
    /**
     * 试题分类id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分类名称
     */
    private String name;
    /**
     * 分类标志图
     */
    private String img;

    /**
     * 分类标志图2
     */
    @Column(name = "cover_img")
    private String coverImg;

    /**
     * 是否隐藏(1显示2隐藏)
     */
    @Column(name = "is_hide")
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
    /**
     * 商品描述
     */
    private String description;

    /**
     * 获取试题分类id
     *
     * @return id - 试题分类id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置试题分类id
     *
     * @param id 试题分类id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取分类名称
     *
     * @return name - 分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置分类名称
     *
     * @param name 分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取是否隐藏(1显示2隐藏)
     *
     * @return is_hide - 是否隐藏(1显示2隐藏)
     */
    public Integer getIsHide() {
        return isHide;
    }

    /**
     * 设置是否隐藏(1显示2隐藏)
     *
     * @param isHide 是否隐藏(1显示2隐藏)
     */
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }
}