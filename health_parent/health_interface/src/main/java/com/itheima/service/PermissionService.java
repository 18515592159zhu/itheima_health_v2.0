package com.itheima.service;

import com.itheima.pojo.CheckItem;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Permission;

import java.util.List;

/*检查项服务接口*/
public interface PermissionService {
    //新增
    void add(Permission permission);
    //分页查询
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //删除
    void delete(Integer id);

    //回显(查询单条)
    Permission findById(Integer id);

    void edit(Permission permission);

    //检查组中动态查询所有权限项
    List<Permission> findAll();
}
