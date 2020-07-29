package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Role;

import java.util.List;

public interface RoleService {
    //新增角色
    void add(Role role, Integer[] permissionIds, Integer[] menuIds);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);


    Role findById(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    void edit(Role role, Integer[] permissionIds, Integer[] menuIds);

    void delete(Integer id);

    List<Role> findAll();

}
