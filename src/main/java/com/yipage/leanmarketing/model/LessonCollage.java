package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lesson_collage")
public class LessonCollage {
    /**
     * 拼课id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 拼课名称
     */
    private String name;

    /**
     * 拼课标题
     */
    private String title;

    /**
     * 拼课封面图
     */
    @Column(name = "cover_img")
    private String coverImg;

    /**
     * 成功拼团人数
     */
    private Integer num;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    private String content;

    /**
     * 拼课起始时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 拼课结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 是否提供场地
     */
    @Column(name = "is_provide_address")
    private Integer isProvideAddress;

    /**
     * 场地
     */
    private String address;
    /**
     * 拼团天数
     */
    private Integer days;

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
    @Transient
    private Integer sum;   //总参团人数

    /**
     * 获取拼课id
     *
     * @return id - 拼课id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置拼课id
     *
     * @param id 拼课id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取拼课名称
     *
     * @return name - 拼课名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置拼课名称
     *
     * @param name 拼课名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取拼课标题
     *
     * @return title - 拼课标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置拼课标题
     *
     * @param title 拼课标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取拼课封面图
     *
     * @return cover_img - 拼课封面图
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * 设置拼课封面图
     *
     * @param coverImg 拼课封面图
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    /**
     * 获取成功拼团人数
     *
     * @return num - 成功拼团人数
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置成功拼团人数
     *
     * @param num 成功拼团人数
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取省
     *
     * @return province - 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取区
     *
     * @return area - 区
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置区
     *
     * @param area 区
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * 获取拼课起始时间
     *
     * @return begin_time - 拼课起始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置拼课起始时间
     *
     * @param beginTime 拼课起始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取拼课结束时间
     *
     * @return end_time - 拼课结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置拼课结束时间
     *
     * @param endTime 拼课结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取是否提供场地
     *
     * @return is_provide_address - 是否提供场地
     */
    public Integer getIsProvideAddress() {
        return isProvideAddress;
    }

    /**
     * 设置是否提供场地
     *
     * @param isProvideAddress 是否提供场地
     */
    public void setIsProvideAddress(Integer isProvideAddress) {
        this.isProvideAddress = isProvideAddress;
    }

    /**
     * 获取场地
     *
     * @return address - 场地
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置场地
     *
     * @param address 场地
     */
    public void setAddress(String address) {
        this.address = address;
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

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}