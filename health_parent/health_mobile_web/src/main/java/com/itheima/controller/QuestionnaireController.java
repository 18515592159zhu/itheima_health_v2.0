package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.Question;
import com.itheima.pojo.Questionnaire;
import com.itheima.pojo.QuestionnaireResult;
import com.itheima.pojo.Result;
import com.itheima.service.MemberService;
import com.itheima.service.QuestionnaireService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/26  20:56
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Reference
    private QuestionnaireService questionnaireService;
    @Reference
    private MemberService memberService;

    @RequestMapping("/findById")
    public Result findById(int id) {
        try {
            Questionnaire questionnaire = questionnaireService.findById(id);
            return new Result(true, MessageConstant.QUERY_QUESTIONNAIRE_SUCCESS, questionnaire);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_QUESTIONNAIRE_FAIL);
        }
    }

    @RequestMapping("/submit")
    public Result submit(@RequestBody List<Question> list, String name, String phoneNumber) {
        try {
            QuestionnaireResult submit = questionnaireService.submit(list, name, phoneNumber);
            return new Result(true, MessageConstant.SUMBIT_QUESTIONNAIRE_SUCCESS, submit);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SUMBIT_QUESTIONNAIRE_FAIL);
        }
    }
}
