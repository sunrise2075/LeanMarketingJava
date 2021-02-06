package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lesson_collage_rule")
public class LessonCollageRule {
    /**
     * 拼课规则
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 拼课规则
     */
    private String content;

    /**
     * 获取拼课规则
     *
     * @return id - 拼课规则
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置拼课规则
     *
     * @param id 拼课规则
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取拼课规则
     *
     * @return content - 拼课规则
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置拼课规则
     *
     * @param content 拼课规则
     */
    public void setContent(String content) {
        this.content = content;
    }
}