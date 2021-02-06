package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_collection_product")
public class UserCollectionProduct {
    /**
     * 用户收藏商品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 收藏的商品id
     */
    @Column(name = "product_id")
    private Integer productId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取用户收藏商品id
     *
     * @return id - 用户收藏商品id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户收藏商品id
     *
     * @param id 用户收藏商品id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取收藏的商品id
     *
     * @return product_id - 收藏的商品id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置收藏的商品id
     *
     * @param productId 收藏的商品id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
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