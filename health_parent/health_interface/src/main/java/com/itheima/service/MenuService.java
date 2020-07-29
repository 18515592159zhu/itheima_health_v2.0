package com.itheima.service;

import com.itheima.pojo.Menu;
import com.itheima.pojo.PageResult;

import java.util.List;
import java.util.Map;

public interface MenuService {
    //获得所有菜单
    List<Menu> getMenuInfo(String username);
    //********************菜单管理中增删改查[开始]*****************************************************//
    void add(Menu menu);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

    //********************菜单管理中增删改查[结束]*****************************************************//
    List<Menu> findAll();
}
