package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {



    Set<Permission> findByRoleId(Integer roleId);


    void add(Permission permission);

    Page<Permission> selectByCondition(String queryString);

    Long findCountByPermissionId(Integer id);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void update(Permission permission);

    List<Permission> findAll();

}
