package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "product_evaluate")
public class ProductEvaluate {
    /**
     * 商品评价id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品id
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 评价描述
     */
    private String description;

    /**
     * 评价人id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 评价图片
     */
    @Column(name = "evaluate_img")
    private String evaluateImg;

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
     * 评价的星的数量
     */
    @Column(name = "star_num")
    private Integer starNum;
    /**
     * 用户的唯一标识
     */
    private String openid;
    /**
     * 订单id
     */
    private Integer orderId;

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    /**
     * 获取商品评价id
     *
     * @return id - 商品评价id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品评价id
     *
     * @param id 商品评价id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * 设置商品id
     *
     * @param productId 商品id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 获取评价描述
     *
     * @return description - 评价描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置评价描述
     *
     * @param description 评价描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取评价人id
     *
     * @return user_id - 评价人id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置评价人id
     *
     * @param userId 评价人id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取评价图片
     *
     * @return evaluate_img - 评价图片
     */
    public String getEvaluateImg() {
        return evaluateImg;
    }

    /**
     * 设置评价图片
     *
     * @param evaluateImg 评价图片
     */
    public void setEvaluateImg(String evaluateImg) {
        this.evaluateImg = evaluateImg;
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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}