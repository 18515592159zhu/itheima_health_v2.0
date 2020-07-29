package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.Result;
import com.itheima.service.QuestionService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Reference
    private QuestionService questionService;


    //分页查询问题功能
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        //调用业务,查询
        try {
            PageResult byPage = questionService.findPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_QUESTION_SUCCESS,byPage);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_QUESTION_FAIL);
        }
    }

}
