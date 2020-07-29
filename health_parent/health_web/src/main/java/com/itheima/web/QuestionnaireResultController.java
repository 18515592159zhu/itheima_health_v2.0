package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.Result;
import com.itheima.service.QuestionnaireResultService;
import com.itheima.service.QuestionnaireService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/27  16:28
 */
@RestController
@RequestMapping("/questionnaireResult")
public class QuestionnaireResultController {

    @Reference
    private QuestionnaireResultService questionnaireResultService;

    //分页查询问题功能
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用业务,查询
        try {
            PageResult byPage = questionnaireResultService.findPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_QUESTIONNAIRE_SUCCESS,byPage);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_QUESTIONNAIRE_FAIL);
        }
    }

}
