package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "administrator_log")
public class AdministratorLog {
    /**
     * 添加操作
     */
    public static final Integer OPERATE_TYPE_ADD = 1;
    /**
     * 修改操作
     */
    public static final Integer OPERATE_TYPE_UPDATE = 2;
    /**
     * 删除操作
     */
    public static final Integer OPERATE_TYPE_DELETE = 3;
    /**
     * 其他操作
     */
    public static final Integer OPERATE_TYPE_OTHER = 4;
    /**
     * 管理员日志id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 操作人id
     */
    @Column(name = "operate_id")
    private Integer operateId;
    /**
     * 操作人ip
     */
    @Column(name = "operate_ip")
    private String operateIp;

    /**
     * 操作人名称
     */
    @Column(name = "operate_name")
    private String operateName;

    /**
     * 操作类型(1增2删3修改4其他)
     */
    @Column(name = "operate_type")
    private Integer operateType;

    /**
     * 操作内容
     */
    @Column(name = "operate_content")
    private String operateContent;

    /**
     * 操作时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取管理员日志id
     *
     * @return id - 管理员日志id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置管理员日志id
     *
     * @param id 管理员日志id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取操作人id
     *
     * @return operate_id - 操作人id
     */
    public Integer getOperateId() {
        return operateId;
    }

    /**
     * 设置操作人id
     *
     * @param operateId 操作人id
     */
    public void setOperateId(Integer operateId) {
        this.operateId = operateId;
    }

    /**
     * 获取操作人名称
     *
     * @return operate_name - 操作人名称
     */
    public String getOperateName() {
        return operateName;
    }

    /**
     * 设置操作人名称
     *
     * @param operateName 操作人名称
     */
    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    /**
     * 获取操作类型(1增2删3修改4查询)
     *
     * @return operate_type - 操作类型(1增2删3修改4查询)
     */
    public Integer getOperateType() {
        return operateType;
    }

    /**
     * 设置操作类型(1增2删3修改4查询)
     *
     * @param operateType 操作类型(1增2删3修改4查询)
     */
    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    /**
     * 获取操作内容
     *
     * @return operate_content - 操作内容
     */
    public String getOperateContent() {
        return operateContent;
    }

    /**
     * 设置操作内容
     *
     * @param operateContent 操作内容
     */
    public void setOperateContent(String operateContent) {
        this.operateContent = operateContent;
    }

    /**
     * 获取操作时间
     *
     * @return create_time - 操作时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置操作时间
     *
     * @param createTime 操作时间
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

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }
}