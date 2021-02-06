package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "exam_score")
public class ExamScore {

    /**
     * 优秀的评语(85分以上)
     */
    public static final String EVALUATE_EXCELLENT = "优秀";
    /**
     * 良好的评语(75分到84分)
     */
    public static final String EVALUATE_GOOD = "良好";

    /**
     * 一般的评语(65分到74)
     */
    public static final String EVALUATE_COMMON = "一般";

    /**
     * 合格的评语(60分到64)
     */
    public static final String EVALUATE_PASS = "合格";

    /**
     * 不合格的评语(小于60分)
     */
    public static final String EVALUATE_NOPASS = "不合格";


    /**
     * 考试成绩id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 科目id
     */
    @Column(name = "subject_id")
    private Integer subjectId;

    /**
     * 考试成绩
     */
    private Integer score;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * 用户的唯一标识
     */
    private String openid;

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 评语
     */
    private String evaluate;
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
     * 试题名称
     */
    @Column(name = "subject_name")
    private String subjectName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * 获取考试成绩id
     *
     * @return id - 考试成绩id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置考试成绩id
     *
     * @param id 考试成绩id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取科目id
     *
     * @return subject_id - 科目id
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * 设置科目id
     *
     * @param subjectId 科目id
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * 获取考试成绩
     *
     * @return score - 考试成绩
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置考试成绩
     *
     * @param score 考试成绩
     */
    public void setScore(Integer score) {
        this.score = score;
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

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}