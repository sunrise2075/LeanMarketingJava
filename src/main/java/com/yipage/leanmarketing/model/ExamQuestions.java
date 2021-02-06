package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "exam_questions")
public class ExamQuestions {
    /**
     * 试题id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 科目id(分类id)
     */
    @Column(name = "subject_id")
    private Integer subjectId;

    /**
     * 试题名称
     */
    private String subjectName;
    /**
     * 试题题目
     */
    private String questions;
    /**
     * 试题题目选项
     */
    private String options;
    /**
     * 试题题目答案
     */
    private String answer;
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
     * 第几道题
     */
    private Integer code;
    private Integer score;
    @Transient
    private List<ExamOption> examOptions;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * 获取试题id
     *
     * @return id - 试题id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置试题id
     *
     * @param id 试题id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取科目id(分类id)
     *
     * @return subject_id - 科目id(分类id)
     */
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * 设置科目id(分类id)
     *
     * @param subjectId 科目id(分类id)
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * 获取试题题目
     *
     * @return questions - 试题题目
     */
    public String getQuestions() {
        return questions;
    }

    /**
     * 设置试题题目
     *
     * @param questions 试题题目
     */
    public void setQuestions(String questions) {
        this.questions = questions;
    }

    /**
     * 获取试题题目选项
     *
     * @return options - 试题题目选项
     */
    public String getOptions() {
        return options;
    }

    /**
     * 设置试题题目选项
     *
     * @param options 试题题目选项
     */
    public void setOptions(String options) {
        this.options = options;
    }

    /**
     * 获取试题题目答案
     *
     * @return answer - 试题题目答案
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置试题题目答案
     *
     * @param answer 试题题目答案
     */
    public void setAnswer(String answer) {
        this.answer = answer;
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public List<ExamOption> getExamOptions() {
        return examOptions;
    }

    public void setExamOptions(List<ExamOption> examOptions) {
        this.examOptions = examOptions;
    }
}