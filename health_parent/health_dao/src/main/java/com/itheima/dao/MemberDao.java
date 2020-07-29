package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;

public interface MemberDao {
    //通过手机号查询会员
    Member findByTelephone(String telephone);

    //新增会员
    void add(Member member);

    //查询当月会员数
    Integer findMemberCountBeforeDate(String m);
    //今日新增会员
    Integer findMemberCountByDate(String date);
    //总会员数
    Integer findMemberTotalCount();

    //本周新增会员数
    Integer findMemberCountAfterDate(String date);

    //根据性别查询会员数量
    Integer findMemberCountByGender(int i);


    //查找0-18岁的会员数量
    Integer findEarlyYoungMember();
    //查找18-30岁的会员数量
    Integer findYoungMember();
    //查找30-45岁的会员数量
    Integer findMiddleAgeMember();
    //查找45以上岁的会员数量
    Integer findElderMember();

    // 分页查询所有会员
    Page<Member> selectByCondition(String queryString);
}
