package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Question;

public interface QuestionDao {

    Page<Question> selectByCondition(String queryString);
}
