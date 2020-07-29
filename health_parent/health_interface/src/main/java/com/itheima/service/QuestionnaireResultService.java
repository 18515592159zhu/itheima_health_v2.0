package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.QuestionnaireResult;

import java.util.Map;

public interface QuestionnaireResultService {

    PageResult findPage(QueryPageBean queryPageBean);

    Map<String,Object> findById(int id);

}
