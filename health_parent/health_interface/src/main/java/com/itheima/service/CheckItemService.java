package com.itheima.service;

import com.itheima.pojo.CheckItem;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.QueryPageBean;

import java.util.List;

/*检查项服务接口*/
public interface CheckItemService {
    //新增
    void add(CheckItem checkItem);
    //分页查询
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //删除
    void delete(Integer id);

    //回显(查询单条)
    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);

    //检查组中动态查询所有检查项
    List<CheckItem> findAll();
}
