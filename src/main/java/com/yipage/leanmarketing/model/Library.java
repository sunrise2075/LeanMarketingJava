package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class Library {

    /**
     * 免费
     */
    public static final Integer IS_FREE = 1;
    /**
     * 收费
     */
    public static final Integer NO_FREE = 2;
    /**
     * 文库内容id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 文库内容标题
     */
    private String title;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;
    /**
     * 文件链接
     */
    private String url;
    /**
     * 分类id
     */
    @Column(name = "category_id")
    private Integer categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 是否免费
     */
    private Integer isFree;
    /**
     * 是否隐藏
     */
    private Integer isHide;
    /**
     * 简介
     */
    private String introduce;
    /**
     * 价格
     */
    private BigDecimal price;
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
     * 文章内容（富文本）
     */
    private String content;
    /**
     * 收费里面的是否免费
     */
    private String codes;
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 是否可以分享(0,1)
     */
    private Integer isShare;
    /**
     * 标签
     */
    private String labels;
    /**
     * 关联的视频id
     */
    private String librarys;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
    }

    /**
     * 获取文库内容id
     *
     * @return id - 文库内容id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置文库内容id
     *
     * @param id 文库内容id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取文库内容标题
     *
     * @return title - 文库内容标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置文库内容标题
     *
     * @param title 文库内容标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取文件类型
     *
     * @return file_type - 文件类型
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * 设置文件类型
     *
     * @param fileType 文件类型
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * 获取分类id
     *
     * @return category_id - 分类id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置分类id
     *
     * @param categoryId 分类id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取简介
     *
     * @return introduce - 简介
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * 设置简介
     *
     * @param introduce 简介
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    /**
     * 获取文章内容（富文本）
     *
     * @return content - 文章内容（富文本）
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文章内容（富文本）
     *
     * @param content 文章内容（富文本）
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getIsShare() {
        return isShare;
    }

    public void setIsShare(Integer isShare) {
        this.isShare = isShare;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getLibrarys() {
        return librarys;
    }

    public void setLibrarys(String librarys) {
        this.librarys = librarys;
    }
}