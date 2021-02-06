package com.yipage.leanmarketing.model;


import javax.persistence.*;
import java.util.Date;

@Table(name = "user")
public class User {
    /**
     * 登录的session
     */
    public static final String LOGIN_USER_SESSION = "login_user";
    /**
     * 已绑定手机状态
     */
    public static final Integer IS_BIND_PHONE = 1;
    /**
     * 未绑定手机状态
     */
    public static final Integer NO_BIND_PHONE = 2;

    /**
     * 用户会员身份
     */
    public static final Integer USER_MENNBER_IDENTITY = 1;
    /**
     * 用户企业身份
     */
    public static final Integer USER_COMPANY_IDENTITY = 2;
    /**
     * 用户代理商身份
     */
    public static final Integer USER_ANGET_IDENTITY = 3;
    /**
     * 经销商身份
     */
    public static final Integer USER_DEALER_IDENTITY = 4;

    /**
     * 首次进入系统
     */
    public static final Integer IS_FIRST = 1;

    public static final Integer NO_FIRST = 2;
    /**
     * 用户id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户微信id
     */
    private String wxid;

    /**
     * 是否绑定手机
     */
    private Integer isBind;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像
     */
    @Column(name = "head_portrait")
    private String headPortrait;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 会员级别（1普通，2初级，3中级，4高级，5企业）
     */
    @Column(name = "member_level")
    private Integer memberLevel;
    /**
     * 注册地区
     */
    @Column(name = "registered_address")
    private String registeredAddress;
    /**
     * 注册时间
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
     * 用户会员权益id
     */
    private Integer userBenefitId;
    /**
     * 用户会员到期时间
     */
    private Date expirationTime;
    /**
     * 姓名(是企业才会有)
     */
    private String name;
    /**
     * 企业名称(是企业才会有)
     */
    private String companyName;
    /**
     * 代理省份
     */
    private String agentProvince;
    /**
     * 绑定的多个手机号
     */
    private String phones;
    /**
     * 订单的多个会员等级
     */
    private String memberLevels;
    /**
     * 是否过期
     */
    private Integer isExpiration;
    /**
     * 来源(1小程序 2 pc端)
     */
    private Integer source;
    @Transient
    private String token;
    /**
     * 上级id
     */
    @Column(name = "superior_id")
    private Integer superiorId;

    @Transient
    private String sessionKey;

    /**
     * 是否首次进入系统
     */
    private Integer isFirst;
    private Integer isDelete;

    public Integer getIsBind() {
        return isBind;
    }

    public void setIsBind(Integer isBind) {
        this.isBind = isBind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getUserBenefitId() {
        return userBenefitId;
    }

    public void setUserBenefitId(Integer userBenefitId) {
        this.userBenefitId = userBenefitId;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    /**
     * 获取用户id
     *
     * @return id - 用户id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置用户id
     *
     * @param id 用户id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * 获取用户微信id
     *
     * @return wxid - 用户微信id
     */
    public String getWxid() {
        return wxid;
    }

    /**
     * 设置用户微信id
     *
     * @param wxid 用户微信id
     */
    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    /**
     * 获取用户昵称
     *
     * @return nickname - 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置用户昵称
     *
     * @param nickname 用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取用户头像
     *
     * @return head_portrait - 用户头像
     */
    public String getHeadPortrait() {
        return headPortrait;
    }

    /**
     * 设置用户头像
     *
     * @param headPortrait 用户头像
     */
    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
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
     * 获取会员级别（1普通，2初级，3中级，4高级，5企业）
     *
     * @return member_level - 会员级别（1普通，2初级，3中级，4高级，5企业）
     */
    public Integer getMemberLevel() {
        return memberLevel;
    }

    /**
     * 设置会员级别（1普通，2初级，3中级，4高级，5企业）
     *
     * @param memberLevel 会员级别（1普通，2初级，3中级，4高级，5企业）
     */
    public void setMemberLevel(Integer memberLevel) {
        this.memberLevel = memberLevel;
    }

    /**
     * 获取注册地区
     *
     * @return registered_address - 注册地区
     */
    public String getRegisteredAddress() {
        return registeredAddress;
    }

    /**
     * 设置注册地区
     *
     * @param registeredAddress 注册地区
     */
    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    /**
     * 获取注册时间
     *
     * @return create_time - 注册时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置注册时间
     *
     * @param createTime 注册时间
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

    public String getAgentProvince() {
        return agentProvince;
    }

    public void setAgentProvince(String agentProvince) {
        this.agentProvince = agentProvince;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getMemberLevels() {
        return memberLevels;
    }

    public void setMemberLevels(String memberLevels) {
        this.memberLevels = memberLevels;
    }

    public Integer getIsExpiration() {
        return isExpiration;
    }

    public void setIsExpiration(Integer isExpiration) {
        this.isExpiration = isExpiration;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(Integer superiorId) {
        this.superiorId = superiorId;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getIsDelete() {  //1表示已删除0表示未删除
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}