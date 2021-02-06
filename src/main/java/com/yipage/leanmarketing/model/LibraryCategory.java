package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "library_category")
public class LibraryCategory {

    /**
     * 隐藏
     */
    public static final Integer IS_HIDE = 2;
    /**
     * 显示
     */
    public static final Integer IS_SHOW = 1;
    /**
     * 文库分类id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文库分类名
     */
    @Column(name = "category_name")
    private String categoryName;

    /**
     * 分类标志图
     */
    private String img;

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
     * 排序
     */
    private Integer sort;

    /**
     * 获取文库分类id
     *
     * @return id - 文库分类id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置文库分类id
     *
     * @param id 文库分类id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取文库分类名
     *
     * @return category_name - 文库分类名
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置文库分类名
     *
     * @param categoryName 文库分类名
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}