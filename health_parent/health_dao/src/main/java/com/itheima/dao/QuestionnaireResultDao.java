package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.QuestionnaireResult;

import java.util.Map;

public interface QuestionnaireResultDao {

    Page<QuestionnaireResult> selectByCondition(String queryString);

    Map<String,Object> findById(int id);
}
