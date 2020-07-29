package com.itheima.utils;

import com.itheima.pojo.Question;
import com.itheima.pojo.QuestionnaireResult;

import java.util.List;

public interface QuestionnaireCalculate {

    QuestionnaireResult calculate(List<Question> list);
}
