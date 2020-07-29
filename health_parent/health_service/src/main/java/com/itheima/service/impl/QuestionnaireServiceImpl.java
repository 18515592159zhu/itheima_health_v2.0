package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MemberDao;
import com.itheima.dao.QuestionnaireDao;
import com.itheima.pojo.*;
import com.itheima.service.QuestionnaireService;
import com.itheima.utils.QuestionnaireCalculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author:  HZ
 * <p>
 * Create:  2019/7/27  9:35
 */
@Service(interfaceClass = QuestionnaireService.class)
@Transactional
public class QuestionnaireServiceImpl implements QuestionnaireService {

    @Autowired
    private QuestionnaireDao questionnaireDao;
    @Autowired
    private QuestionnaireCalculate questionnaireCalculate;
    @Autowired
    private MemberDao memberDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Questionnaire> page = questionnaireDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Questionnaire findById(int id) {
        return questionnaireDao.findById(id);
    }

    @Override
    public QuestionnaireResult submit(List<Question> list, String name, String phoneNumber) {
        Member member = memberDao.findByTelephone(phoneNumber);
        if(member == null){
            member = new Member();
            member.setName(name);
            member.setPhoneNumber(phoneNumber);
            member.setRegTime(new Date());
            memberDao.add(member);
        }
        QuestionnaireResult calculated = questionnaireCalculate.calculate(list);
        calculated.setMember_id(member.getId());
        questionnaireDao.generateResults(calculated);
        return calculated;

    }
}
