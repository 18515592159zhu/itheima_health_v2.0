package com.itheima.service;

import com.itheima.pojo.*;

import java.util.List;

public interface QuestionnaireService {

    PageResult findPage(QueryPageBean queryPageBean);

    Questionnaire findById(int id);

    QuestionnaireResult submit(List<Question> list, String name, String phoneNumber);
}
