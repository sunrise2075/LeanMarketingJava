package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "user_benefit")
public class UserBenefit {
    /**
     * 会员权益id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员权益名称
     */
    private String name;

    /**
     * 会员权益说明
     */
    private String introduction;

    /**
     * 会员权益类型(1个人会员2企业会员)
     */
    private Integer type;

    /**
     * 半年的价格
     */
    @Column(name = "half_year_price")
    private BigDecimal halfYearPrice;

    /**
     * 一年的价格
     */
    @Column(name = "year_price")
    private BigDecimal yearPrice;

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
     * 对应的会员等级
     */
    private Integer memberLevel;
    /**
     * 职员数量
     */
    private Integer clerkNum;
    /**
     * 总监数量
     */
    private Integer directorNum;

    /**
     * 获取会员权益id
     *
     * @return id - 会员权益id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置会员权益id
     *
     * @param id 会员权益id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取会员权益名称
     *
     * @return name - 会员权益名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置会员权益名称
     *
     * @param name 会员权益名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取会员权益说明
     *
     * @return introduction - 会员权益说明
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置会员权益说明
     *
     * @param introduction 会员权益说明
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * 获取会员权益类型(1个人会员2企业会员)
     *
     * @return type - 会员权益类型(1个人会员2企业会员)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置会员权益类型(1个人会员2企业会员)
     *
     * @param type 会员权益类型(1个人会员2企业会员)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取半年的价格
     *
     * @return half_year_price - 半年的价格
     */
    public BigDecimal getHalfYearPrice() {
        return halfYearPrice;
    }

    /**
     * 设置半年的价格
     *
     * @param halfYearPrice 半年的价格
     */
    public void setHalfYearPrice(BigDecimal halfYearPrice) {
        this.halfYearPrice = halfYearPrice;
    }

    /**
     * 获取一年的价格
     *
     * @return year_price - 一年的价格
     */
    public BigDecimal getYearPrice() {
        return yearPrice;
    }

    /**
     * 设置一年的价格
     *
     * @param yearPrice 一年的价格
     */
    public void setYearPrice(BigDecimal yearPrice) {
        this.yearPrice = yearPrice;
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

    public Integer getMemberLevel() {
        return memberLevel;
    }

    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    public Integer getClerkNum() {
        return clerkNum;
    }

    public void setClerkNum(Integer clerkNum) {
        this.clerkNum = clerkNum;
    }

    public Integer getDirectorNum() {
        return directorNum;
    }

    public void setDirectorNum(Integer directorNum) {
        this.directorNum = directorNum;
    }
}