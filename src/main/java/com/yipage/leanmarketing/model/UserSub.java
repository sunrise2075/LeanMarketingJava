package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_sub")
public class UserSub {
    /**
     * 子用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户的openid
     */
    private String openid;

    /**
     * 会员等级
     */
    @Column(name = "member_level")
    private Integer memberLevel;

    /**
     * 权益id
     */
    @Column(name = "benfit_id")
    private Integer benfitId;

    /**
     * q申请人名称
     */
    @Column(name = "apply_name")
    private String applyName;

    /**
     * 过期时间
     */
    @Column(name = "expiration_time")
    private Date expirationTime;

    /**
     * 公司名称
     */
    @Column(name = "company_name")
    private String companyName;

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
     * 用户身份
     */
    private Integer identity;
    /**
     * 代理省份
     */
    private String agentProvince;
    /**
     * 上级id
     */
    @Column(name = "superior_id")
    private Integer superiorId;

    /**
     * 获取子用户id
     *
     * @return id - 子用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置子用户id
     *
     * @param id 子用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取用户的openid
     *
     * @return openid - 用户的openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * 设置用户的openid
     *
     * @param openid 用户的openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * 获取会员等级
     *
     * @return member_level - 会员等级
     */
    public Integer getMemberLevel() {
        return memberLevel;
    }

    /**
     * 设置会员等级
     *
     * @param memberLevel 会员等级
     */
    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    /**
     * 获取权益id
     *
     * @return benfit_id - 权益id
     */
    public Integer getBenfitId() {
        return benfitId;
    }

    /**
     * 设置权益id
     *
     * @param benfitId 权益id
     */
    public void setBenfitId(Integer benfitId) {
        this.benfitId = benfitId;
    }

    /**
     * 获取q申请人名称
     *
     * @return apply_name - q申请人名称
     */
    public String getApplyName() {
        return applyName;
    }

    /**
     * 设置q申请人名称
     *
     * @param applyName q申请人名称
     */
    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    /**
     * 获取过期时间
     *
     * @return expiration_time - 过期时间
     */
    public Date getExpirationTime() {
        return expirationTime;
    }

    /**
     * 设置过期时间
     *
     * @param expirationTime 过期时间
     */
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    /**
     * 获取公司名称
     *
     * @return company_name - 公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置公司名称
     *
     * @param companyName 公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getAgentProvince() {
        return agentProvince;
    }

    public void setAgentProvince(String agentProvince) {
        this.agentProvince = agentProvince;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

}