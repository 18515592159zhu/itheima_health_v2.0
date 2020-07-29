package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.Result;
import com.itheima.service.QuestionnaireResultService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/26  20:56
 */
@RestController
@RequestMapping("/questionnaireResult")
public class QuestionnaireResultController {

    @Reference
    private QuestionnaireResultService questionnaireResultService;


    @RequestMapping("/findById")
    public Result findById(int id) {
        try {
            Map<String,Object> result = questionnaireResultService.findById(id);
            return new Result(true, MessageConstant.SUMBIT_QUESTIONNAIRERESULT_SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SUMBIT_QUESTIONNAIRERESULT_FAIL);
        }
    }

}
