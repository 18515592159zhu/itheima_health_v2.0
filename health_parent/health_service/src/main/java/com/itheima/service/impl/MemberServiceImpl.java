package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Member;
import com.itheima.pojo.PageResult;
import com.itheima.service.MemberService;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-18-16:02
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        //密码加密
        if(member.getPassword() != null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> month) {

        List<Integer> list = new ArrayList<>();
        for (String m : month) {
            m = m + ".31";
            Integer count =  memberDao.findMemberCountBeforeDate(m);
            list.add(count);
        }

        return list;
    }

    /**
     * 根据性别统计会员数和比例
     * @return
     */
    @Override
    public List<Map<String, Object>> findMemberByGender() {
        Map<String,Object> map1 = new HashMap<>();
        Map<String,Object> map2 = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        Integer maleMemberCount = memberDao.findMemberCountByGender(1);
        map1.put("value",maleMemberCount);
        map1.put("name","男性会员");
        list.add(map1);
        Integer femaleMemberCount = memberDao.findMemberCountByGender(2);
        map2.put("value",femaleMemberCount);
        map2.put("name","女性会员");
        list.add(map2);
        return list;
    }

    /**
     * 根据会员年龄统计不同年龄段会员人数
     * @return
     */
    @Override
    public List<Map<String, Object>> findMemberCountByAge() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> resultMap1 = new HashMap<>();
        Integer earlyYoungMember = memberDao.findEarlyYoungMember();
        resultMap1.put("name","0-18");
        resultMap1.put("value",earlyYoungMember);
        list.add(resultMap1);

        Map<String, Object> resultMap2 = new HashMap<>();
        Integer youngMember = memberDao.findYoungMember();
        resultMap2.put("name","18-30");
        resultMap2.put("value",youngMember);
        list.add(resultMap2);

        Map<String, Object> resultMap3 = new HashMap<>();
        Integer middleAgeMember = memberDao.findMiddleAgeMember();
        resultMap3.put("name","30-45");
        resultMap3.put("value",youngMember);
        list.add(resultMap3);


        Map<String, Object> resultMap4 = new HashMap<>();
        Integer elderMember = memberDao.findElderMember();
        resultMap4.put("name","45以上");
        resultMap4.put("value",youngMember);
        list.add(resultMap4);

        return list;
    }

    /**
     * 分页查询所有会员
     */
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //mybatis分页助手插件
        PageHelper.startPage(currentPage,pageSize);
        if (queryString != null && queryString.length() > 0) {
            queryString = "%" + queryString + "%";
        }
        Page<Member> page = memberDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}
