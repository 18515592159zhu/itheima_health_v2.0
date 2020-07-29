package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.QuestionnaireResultDao;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.QuestionnaireResult;
import com.itheima.service.QuestionnaireResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/27  16:31
 */
@Service(interfaceClass = QuestionnaireResultService.class)
@Transactional
public class QuestionnaireResultServiceImpl implements QuestionnaireResultService {

    @Autowired
    private QuestionnaireResultDao questionnaireResultDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<QuestionnaireResult> page = questionnaireResultDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Map<String,Object> findById(int id) {
        return questionnaireResultDao.findById(id);
    }
}
