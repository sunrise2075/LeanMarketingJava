package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "library_download_record")
public class LibraryDownloadRecord {
    /**
     * 文库下载记录
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    private String openid;

    /**
     * 文库id
     */
    @Column(name = "library_id")
    private Integer libraryId;

    /**
     * 文库标题
     */
    @Column(name = "library_title")
    private String libraryTitle;

    /**
     * 文件大小(k为单位)
     */
    @Column(name = "file_size")
    private Long fileSize;

    /**
     * 文件类型
     */
    @Column(name = "file_type")
    private String fileType;


    /**
     * 文件url
     */
    private String url;

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
     * 获取文库下载记录
     *
     * @return id - 文库下载记录
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置文库下载记录
     *
     * @param id 文库下载记录
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取文库id
     *
     * @return library_id - 文库id
     */
    public Integer getLibraryId() {
        return libraryId;
    }

    /**
     * 设置文库id
     *
     * @param libraryId 文库id
     */
    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    /**
     * 获取文库标题
     *
     * @return library_title - 文库标题
     */
    public String getLibraryTitle() {
        return libraryTitle;
    }

    /**
     * 设置文库标题
     *
     * @param libraryTitle 文库标题
     */
    public void setLibraryTitle(String libraryTitle) {
        this.libraryTitle = libraryTitle;
    }

    /**
     * 获取文件大小(k为单位)
     *
     * @return file_size - 文件大小(k为单位)
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 设置文件大小(k为单位)
     *
     * @param fileSize 文件大小(k为单位)
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}