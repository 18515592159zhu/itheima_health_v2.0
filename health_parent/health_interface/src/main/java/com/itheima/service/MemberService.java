package com.itheima.service;

import com.itheima.pojo.Member;
import com.itheima.pojo.PageResult;

import java.util.List;
import java.util.Map;

public interface MemberService {
    //根据手机号判断是否是会员
    Member findByTelephone(String telephone);

    //新增会员
    void add(Member member);

    //根据月份查询会员数
    List<Integer> findMemberCountByMonth(List<String> list);

    //根据性别查询会员数
    List<Map<String, Object>> findMemberByGender();

    //根据年龄段查询会员数量
    List<Map<String, Object>> findMemberCountByAge();

    // 分页查询
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);



}
