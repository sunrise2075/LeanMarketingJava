package com.yipage.leanmarketing.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Banner {

    /**
     * 禁用
     */
    public static final Integer NO_USE = 2;
    /**
     * 启用
     */
    public static final Integer IS_USE = 1;
    /**
     * 轮播图id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 类型id
     */
    @Column(name = "type_id")
    private Integer typeId;

    private Integer type;//当type为1时是商品轮播图,为2时是视频轮播图
    /**
     * 标题
     */
    private String title;
    /**
     * 图片
     */
    private String img;
    /**
     * 图片
     */
    private String imgpc;
    /**
     * 1启用2禁用
     */
    private Integer state;
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取轮播图id
     *
     * @return id - 轮播图id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置轮播图id
     *
     * @param id 轮播图id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取图片
     *
     * @return img - 图片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置图片
     *
     * @param img 图片
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取1启用2禁用
     *
     * @return state - 1启用2禁用
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置1启用2禁用
     *
     * @param state 1启用2禁用
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

    public String getImgpc() {
        return imgpc;
    }

    public void setImgpc(String imgpc) {
        this.imgpc = imgpc;
    }
}