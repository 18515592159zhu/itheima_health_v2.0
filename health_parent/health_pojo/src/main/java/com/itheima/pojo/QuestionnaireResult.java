package com.itheima.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/27  10:45
 */
public class QuestionnaireResult implements Serializable {

    private Integer id;
    private String fileCode;
    private Date date;
    private int pinghe;
    private int qixu;
    private int yangxu;
    private int yinxu;
    private int tanshi;
    private int shire;
    private int xueyu;
    private int qiyu;
    private int tebing;
    private int member_id;

    public QuestionnaireResult() {
    }

    public QuestionnaireResult(String fileCode, Date date, int pinghe, int qixu, int yangxu, int yinxu, int tanshi, int shire, int xueyu, int qiyu, int tebing) {
        this.fileCode = fileCode;
        this.date = date;
        this.pinghe = pinghe;
        this.qixu = qixu;
        this.yangxu = yangxu;
        this.yinxu = yinxu;
        this.tanshi = tanshi;
        this.shire = shire;
        this.xueyu = xueyu;
        this.qiyu = qiyu;
        this.tebing = tebing;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPinghe() {
        return pinghe;
    }

    public void setPinghe(int pinghe) {
        this.pinghe = pinghe;
    }

    public int getQixu() {
        return qixu;
    }

    public void setQixu(int qixu) {
        this.qixu = qixu;
    }

    public int getYangxu() {
        return yangxu;
    }

    public void setYangxu(int yangxu) {
        this.yangxu = yangxu;
    }

    public int getYinxu() {
        return yinxu;
    }

    public void setYinxu(int yinxu) {
        this.yinxu = yinxu;
    }

    public int getTanshi() {
        return tanshi;
    }

    public void setTanshi(int tanshi) {
        this.tanshi = tanshi;
    }

    public int getShire() {
        return shire;
    }

    public void setShire(int shire) {
        this.shire = shire;
    }

    public int getXueyu() {
        return xueyu;
    }

    public void setXueyu(int xueyu) {
        this.xueyu = xueyu;
    }

    public int getQiyu() {
        return qiyu;
    }

    public void setQiyu(int qiyu) {
        this.qiyu = qiyu;
    }

    public int getTebing() {
        return tebing;
    }

    public void setTebing(int tebing) {
        this.tebing = tebing;
    }
}
