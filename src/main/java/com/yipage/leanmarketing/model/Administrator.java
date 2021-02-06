package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


public class Administrator {

    /**
     * 禁用
     */
    public static final Integer NO_USE = 2;
    /**
     * 启用
     */
    public static final Integer IS_USE = 1;

    public static final String LOGIN_ADMIN_SESSION = "login_admin";

    public static final Integer ADMINISTRATOR_ROLE = 1;
    public static final Integer AGENT_ROLE = 2;
    public static final Integer COMPANY_ROLE = 3;
    public static final Integer Dealer_ROLE = 4;
    /**
     * 后台管理员id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 后台管理员名称
     */
    private String username;

    /**
     * 后台管理员
     */
    private String password;

    /**
     * 管理员头像
     */
    @Column(name = "head_portrait")
    private String headPortrait;

    /**
     * 是否禁用(1启用,2禁用)
     */
    private Integer state;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 手机
     */
    private String phone;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 角色名称
     */
    private String roleName;
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
     * 用户ID(如果是企业会员)
     */
    private Integer userId;
    /**
     * 与角色标的唯一标识
     */
    private Integer roleCode;
    /**
     * 代理商身份的代理地址
     */
    private String address;
    private Integer identityId;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 获取后台管理员id
     *
     * @return id - 后台管理员id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置后台管理员id
     *
     * @param id 后台管理员id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取后台管理员名称
     *
     * @return username - 后台管理员名称
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置后台管理员名称
     *
     * @param username 后台管理员名称
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取后台管理员
     *
     * @return password - 后台管理员
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置后台管理员
     *
     * @param password 后台管理员
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取管理员头像
     *
     * @return head_portrait - 管理员头像
     */
    public String getHeadPortrait() {
        return headPortrait;
    }

    /**
     * 设置管理员头像
     *
     * @param headPortrait 管理员头像
     */
    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    /**
     * 获取是否禁用(1启用,2禁用)
     *
     * @return state - 是否禁用(1启用,2禁用)
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置是否禁用(1启用,2禁用)
     *
     * @param state 是否禁用(1启用,2禁用)
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取邮箱
     *
     * @return mail - 邮箱
     */
    public String getMail() {
        return mail;
    }

    /**
     * 设置邮箱
     *
     * @param mail 邮箱
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * 获取手机
     *
     * @return phone - 手机
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机
     *
     * @param phone 手机
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取角色id
     *
     * @return role_id - 角色id
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色id
     *
     * @param roleId 角色id
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIdentityId() {
        return identityId;
    }

    public void setIdentityId(Integer identityId) {
        this.identityId = identityId;
    }

}