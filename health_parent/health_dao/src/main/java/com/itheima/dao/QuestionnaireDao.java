package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Questionnaire;
import com.itheima.pojo.QuestionnaireResult;

public interface QuestionnaireDao {

    Page<Questionnaire> selectByCondition(String queryString);

    Questionnaire findById(int id);

    void generateResults(QuestionnaireResult calculated);
}
