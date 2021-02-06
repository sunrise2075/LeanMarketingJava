package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "product_category")
public class ProductCategory {

    /**
     * 隐藏
     */
    public static final Integer IS_HIDE = 2;
    /**
     * 显示
     */
    public static final Integer IS_SHOW = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品分类
     */
    private String name;

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
     * 获取商品分类
     *
     * @return name - 商品分类
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品分类
     *
     * @param name 商品分类
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
}