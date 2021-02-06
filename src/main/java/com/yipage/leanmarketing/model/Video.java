package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class Video {
    /**
     * 免费课程
     */
    public static final Integer IS_FREE = 1;
    /**
     * 收费课程
     */
    public static final Integer NO_FREE = 2;

    /**
     * 显示
     */
    public static final Integer IS_SHOW = 1;
    /**
     * 隐藏
     */
    public static final Integer IS_HIDE = 2;

    /**
     * 视频id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 视频标题
     */
    private String title;

    /**
     * 作者
     */
    @Column(name = "author_name")
    private String authorName;

    /**
     * 作者头像
     */
    @Column(name = "author_img")
    private String authorImg;

    /**
     * 作者介绍
     */
    @Column(name = "author_introduce")
    private String authorIntroduce;

    /**
     * （2收费，1免费）
     */
    @Column(name = "is_free")
    private Integer isFree;

    /**
     * 是否隐藏(1显示2隐藏)
     */
    private Integer isHide;
    /**
     * 销售量
     */
    @Column(name = "sales_num")
    private Integer salesNum;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 详情描述
     */
    private String description;
    /**
     * 视频连接
     */
    @Column(name = "video_url")
    private String videoUrl;
    /**
     * 封面图
     */
    @Column(name = "cover_img")
    private String coverImg;
    /**
     * 视频类别id
     */
    @Column(name = "category_id")
    private Integer categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
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
     * 收费里面的是否免费
     */
    private String codes;
    /**
     * 视频详情(富文本)
     */
    private String content;
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
    private String videos;

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
    }

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 获取视频id
     *
     * @return id - 视频id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置视频id
     *
     * @param id 视频id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取视频标题
     *
     * @return title - 视频标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置视频标题
     *
     * @param title 视频标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取作者
     *
     * @return author_name - 作者
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * 设置作者
     *
     * @param authorName 作者
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * 获取作者头像
     *
     * @return author_img - 作者头像
     */
    public String getAuthorImg() {
        return authorImg;
    }

    /**
     * 设置作者头像
     *
     * @param authorImg 作者头像
     */
    public void setAuthorImg(String authorImg) {
        this.authorImg = authorImg;
    }

    /**
     * 获取作者介绍
     *
     * @return author_introduce - 作者介绍
     */
    public String getAuthorIntroduce() {
        return authorIntroduce;
    }

    /**
     * 设置作者介绍
     *
     * @param authorIntroduce 作者介绍
     */
    public void setAuthorIntroduce(String authorIntroduce) {
        this.authorIntroduce = authorIntroduce;
    }

    /**
     * 获取（（2收费，1免费）
     *
     * @return is_free - （（2收费，1免费）
     */
    public Integer getIsFree() {
        return isFree;
    }

    /**
     * 设置（2收费，1免费）
     *
     * @param isFree （2收费，1免费）
     */
    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
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
     * 获取详情描述
     *
     * @return description - 详情描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置详情描述
     *
     * @param description 详情描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取视频连接
     *
     * @return video_url - 视频连接
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * 设置视频连接
     *
     * @param videoUrl 视频连接
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * 获取封面图
     *
     * @return cover_img - 封面图
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * 设置封面图
     *
     * @param coverImg 封面图
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    /**
     * 获取视频类别id
     *
     * @return category_id - 视频类别id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置视频类别id
     *
     * @param categoryId 视频类别id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }
}