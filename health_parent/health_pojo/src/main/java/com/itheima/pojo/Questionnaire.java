package com.itheima.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/26  21:36
 */
public class Questionnaire implements Serializable {

    private Integer id;
    private String name; // 问卷名称
    private String type; // 问卷类型
    private String enable; // 是否启用
    private String sex; // 适用性别

    private List<Question> questions; // 包含的问题集合

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
