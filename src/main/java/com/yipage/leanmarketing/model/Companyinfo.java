package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Companyinfo {
    /**
     * 公司信息id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String introduction;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;
    @Column(name = "cover_img")
    private String coverImg;

    @Column(name = "pc_img")
    private String pcImg;
    @Column(name = "about_us")
    private String aboutUs;
    @Column(name = "user_item")
    private String userItem;
    @Column(name = "vip_rights")
    private String vipRights;
    /**
     * logo
     */
    private String logo;
    /**
     * 登录背景图
     */
    @Column(name = "login_bg_img")
    private String loginBgImg;

    public String getPcImg() {
        return pcImg;
    }

    public void setPcImg(String pcImg) {
        this.pcImg = pcImg;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    /**
     * 获取公司信息id
     *
     * @return id - 公司信息id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置公司信息id
     *
     * @param id 公司信息id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
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
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getUserItem() {
        return userItem;
    }

    public void setUserItem(String userItem) {
        this.userItem = userItem;
    }

    public String getVipRights() {
        return vipRights;
    }

    public void setVipRights(String vipRights) {
        this.vipRights = vipRights;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLoginBgImg() {
        return loginBgImg;
    }

    public void setLoginBgImg(String loginBgImg) {
        this.loginBgImg = loginBgImg;
    }
}