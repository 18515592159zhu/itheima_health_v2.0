package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.QuestionDao;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.Question;
import com.itheima.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/26  22:30
 */
@Service(interfaceClass= QuestionService.class)
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Question> page = questionDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }
}
