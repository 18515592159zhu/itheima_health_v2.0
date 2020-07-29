package com.itheima.pojo;

import java.io.Serializable;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/26  21:32
 */
public class Question implements Serializable {

    private Integer id;
    private String name; // 问题名称
    private String type; // 问题类型
    private String sex; // 使用性别
    private String subType; // 中医问卷子分类
    private int reversal; // 是否需要翻转分数
    private String required; // 是否必填
    private int value; // 表示分值

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public int getReversal() {
        return reversal;
    }

    public void setReversal(int reversal) {
        this.reversal = reversal;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", sex='" + sex + '\'' +
                ", subType='" + subType + '\'' +
                ", reversal=" + reversal +
                ", required='" + required + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
