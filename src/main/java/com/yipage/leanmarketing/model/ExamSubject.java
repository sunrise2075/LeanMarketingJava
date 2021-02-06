package com.yipage.leanmarketing.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "exam_subject")
public class ExamSubject {
    /**
     * 免费
     */
    public static final Integer IS_FREE = 1;
    /**
     * 收费
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
     * 科目id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 试题总名称
     */
    private String name;
    /**
     * 试题价格
     */
    private BigDecimal price;
    /**
     * 分类id
     */
    @Column(name = "category_id")
    private Integer categoryId;
    /**
     * 试题分类名称
     */
    private String categoryName;
    /**
     * 是否隐藏(1显示2隐藏)
     */
    @Column(name = "is_hide")
    private Integer isHide;
    private Integer isFree;
    /**
     * 考试时长
     */
    @Column(name = "exam_duration")
    private Integer examDuration;
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
     * 问题数量
     */
    private Integer questionNum;
    private String codes;
    /**
     * 是否可以分享(0,1)
     */
    private Integer isShare;
    private Integer type;
    /**
     * 标签
     */
    private String labels;
    /**
     * 关联的试题id
     */
    private String exams;
    /**
     * 能力标签
     */
    private String abilitys;
    /**
     * 能力标签名称
     */
    private String abilityNames;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    /**
     * 获取科目id
     *
     * @return id - 科目id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置科目id
     *
     * @param id 科目id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取试题总名称
     *
     * @return name - 试题总名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置试题总名称
     *
     * @param name 试题总名称
     */
    public void setName(String name) {
        this.name = name;
    }

    //附加字段

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
     * 获取考试时长
     *
     * @return exam_duration - 考试时长
     */
    public Integer getExamDuration() {
        return examDuration;
    }

    /**
     * 设置考试时长
     *
     * @param examDuration 考试时长
     */
    public void setExamDuration(Integer examDuration) {
        this.examDuration = examDuration;
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

    public Integer getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public Integer getIsShare() {
        return isShare;
    }

    public void setIsShare(Integer isShare) {
        this.isShare = isShare;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getExams() {
        return exams;
    }

    public void setExams(String exams) {
        this.exams = exams;
    }

    public String getAbilitys() {
        return abilitys;
    }

    public void setAbilitys(String abilitys) {
        this.abilitys = abilitys;
    }

    public String getAbilityNames() {
        return abilityNames;
    }

    public void setAbilityNames(String abilityNames) {
        this.abilityNames = abilityNames;
    }
}