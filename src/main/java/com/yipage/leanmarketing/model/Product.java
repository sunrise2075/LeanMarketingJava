package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

public class Product {
    /**
     * 隐藏
     */
    public static final Integer IS_HIDE = 2;
    /**
     * 显示
     */
    public static final Integer IS_SHOW = 1;

    /**
     * 免费
     */
    public static final Integer IS_FREE = 1;
    /**
     * 收费
     */
    public static final Integer NO_FREE = 2;

    /**
     * 上架状态
     */
    public static final Integer STATE_UP = 1;

    /**
     * 下架状态
     */
    public static final Integer STATE_DOWN = 2;

    /**
     * 商品id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商品分类id
     */
    private Integer categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 商品简介
     */
    private String introduction;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品封面图
     */
    @Column(name = "cover_img")
    private String coverImg;
    /**
     * 商品详情图
     */
    private String bannerImg;
    /**
     * 普通会员和企业对应的商品价格
     */
    @Column(name = "price_normal")
    private BigDecimal priceNormal;
    private BigDecimal price;
    /**
     * 初级会员对应的商品价格
     */
    @Column(name = "price_junior")
    private BigDecimal priceJunior;
    /**
     * 中级会员对应的商品价格
     */
    @Column(name = "price_mid")
    private BigDecimal priceMid;
    /**
     * 高级会员对应的商品价格
     */
    @Column(name = "price_high")
    private BigDecimal priceHigh;
    /**
     * 作者名
     */
    @Column(name = "author_name")
    private String authorName;
    /**
     * 作者头像
     */
    @Column(name = "author_img")
    private String authorImg;
    /**
     * 作者简介
     */
    @Column(name = "author_introduce")
    private String authorIntroduce;
    /**
     * 出版社
     */
    @Column(name = "publishing_house")
    private String publishingHouse;
    /**
     * 页数
     */
    @Column(name = "page_number")
    private Integer pageNumber;
    /**
     * 字数
     */
    @Column(name = "word_number")
    private Integer wordNumber;
    /**
     * 是否套装（1是2否）
     */
    private Integer suit;
    /**
     * 商品状态(1上架 2下架)
     */
    private Integer state;
    /**
     * 是否隐藏
     */
    private Integer isHide;
    /**
     * 销售数量
     */
    private Integer salesNum;
    private Integer isFree;
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
     * 出版时间
     */
    private String publishTime;
    /**
     * 国际标准书号
     */
    private String bookNumber;
    /**
     * 图文详情（富文本）
     */
    private String description;
    private String address;
    @Column(name = "recommend_img")
    private String recommendImg;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(Integer salesNum) {
        this.salesNum = salesNum;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取商品id
     *
     * @return id - 商品id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置商品id
     *
     * @param id 商品id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品名称
     *
     * @return name - 商品名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置商品名称
     *
     * @param name 商品名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取商品标题
     *
     * @return title - 商品标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置商品标题
     *
     * @param title 商品标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取商品封面图
     *
     * @return cover_img - 商品封面图
     */
    public String getCoverImg() {
        return coverImg;
    }

    /**
     * 设置商品封面图
     *
     * @param coverImg 商品封面图
     */
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    /**
     * 获取普通会员和企业对应的商品价格
     *
     * @return price_normal - 普通会员和企业对应的商品价格
     */
    public BigDecimal getPriceNormal() {
        return priceNormal;
    }

    /**
     * 设置普通会员和企业对应的商品价格
     *
     * @param priceNormal 普通会员和企业对应的商品价格
     */
    public void setPriceNormal(BigDecimal priceNormal) {
        this.priceNormal = priceNormal;
    }

    /**
     * 获取初级会员对应的商品价格
     *
     * @return price_junior - 初级会员对应的商品价格
     */
    public BigDecimal getPriceJunior() {
        return priceJunior;
    }

    /**
     * 设置初级会员对应的商品价格
     *
     * @param priceJunior 初级会员对应的商品价格
     */
    public void setPriceJunior(BigDecimal priceJunior) {
        this.priceJunior = priceJunior;
    }

    /**
     * 获取中级会员对应的商品价格
     *
     * @return price_mid - 中级会员对应的商品价格
     */
    public BigDecimal getPriceMid() {
        return priceMid;
    }

    /**
     * 设置中级会员对应的商品价格
     *
     * @param priceMid 中级会员对应的商品价格
     */
    public void setPriceMid(BigDecimal priceMid) {
        this.priceMid = priceMid;
    }

    /**
     * 获取高级会员对应的商品价格
     *
     * @return price_high - 高级会员对应的商品价格
     */
    public BigDecimal getPriceHigh() {
        return priceHigh;
    }

    /**
     * 设置高级会员对应的商品价格
     *
     * @param priceHigh 高级会员对应的商品价格
     */
    public void setPriceHigh(BigDecimal priceHigh) {
        this.priceHigh = priceHigh;
    }

    /**
     * 获取作者名
     *
     * @return author_name - 作者名
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * 设置作者名
     *
     * @param authorName 作者名
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
     * 获取作者简介
     *
     * @return author_introduce - 作者简介
     */
    public String getAuthorIntroduce() {
        return authorIntroduce;
    }

    /**
     * 设置作者简介
     *
     * @param authorIntroduce 作者简介
     */
    public void setAuthorIntroduce(String authorIntroduce) {
        this.authorIntroduce = authorIntroduce;
    }

    /**
     * 获取出版社
     *
     * @return publishing_house - 出版社
     */
    public String getPublishingHouse() {
        return publishingHouse;
    }

    /**
     * 设置出版社
     *
     * @param publishingHouse 出版社
     */
    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    /**
     * 获取页数
     *
     * @return page_number - 页数
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置页数
     *
     * @param pageNumber 页数
     */
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 获取字数
     *
     * @return word_number - 字数
     */
    public Integer getWordNumber() {
        return wordNumber;
    }

    /**
     * 设置字数
     *
     * @param wordNumber 字数
     */
    public void setWordNumber(Integer wordNumber) {
        this.wordNumber = wordNumber;
    }

    /**
     * 获取是否套装（1是2否）
     *
     * @return suit - 是否套装（1是2否）
     */
    public Integer getSuit() {
        return suit;
    }

    /**
     * 设置是否套装（1是2否）
     *
     * @param suit 是否套装（1是2否）
     */
    public void setSuit(Integer suit) {
        this.suit = suit;
    }

    /**
     * 获取商品状态(1上架 2下架)
     *
     * @return state - 商品状态(1上架 2下架)
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置商品状态(1上架 2下架)
     *
     * @param state 商品状态(1上架 2下架)
     */
    public void setState(Integer state) {
        this.state = state;
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
     * 获取图文详情（富文本）
     *
     * @return description - 图文详情（富文本）
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置图文详情（富文本）
     *
     * @param description 图文详情（富文本）
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getBookNumber() {
        return bookNumber;
    }

    public void setBookNumber(String bookNumber) {
        this.bookNumber = bookNumber;
    }

    public String getRecommendImg() {
        return recommendImg;
    }

    public void setRecommendImg(String recommendImg) {
        this.recommendImg = recommendImg;
    }
}