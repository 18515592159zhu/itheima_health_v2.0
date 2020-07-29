package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.User;

import java.util.List;

public interface UserService {
    /**
     * 根据用户名查询User
     *
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    //************用户增删改查[开始]***********
    void add(User user, Integer[] roleIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    User findById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(User user, Integer[] roleIds);

    //根据用户名修改密码
    void chengedByUsername(String username, String password);

    //************用户增删改查[结束]***********
}
