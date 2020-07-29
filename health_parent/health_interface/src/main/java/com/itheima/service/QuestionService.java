package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.QueryPageBean;

public interface QuestionService {

    PageResult findPage(QueryPageBean queryPageBean);
}
