package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleDao {

    Set<Role> findByUid(Integer id);

    //根据用户名查询角色ID
    Integer[] findRidByUsername(String username);


//************************增删改查[开始]************************************

    void add(Role role);

    //角色权限中间表
    void setRoleAndPermission(Map<String, Integer> map);
    //角色菜单中间表
    void setRoleAndMenu(@Param("map") Map<String, Integer> map);
    //分页查询
    Page<Role> selectByCondition(String queryString);
    //根据角色ID查询角色对象
    Role findById(Integer id);
    //根据角色ID查询所有权限项对象
    List<Integer> findPermissionIdsByRoleId(Integer id);
    //根据角色ID查询所有菜单项对象
    List<Integer> findMenuIdsByRoleId(Integer id);
    //删除权限关联表
    void deletePermissionAssociation(Integer id);
    //删除菜单关联表
    void deleteMenuAssociation(Integer id);
    //编辑角色
    void edit(Role role);
    //删除角色
    void delete(Integer id);

//************************增删改查[结束]************************************
    //新增用户
    List<Role> findAll();

    /**
     * 根据角色id,查询关联的菜单id
     * @param rid
     * @return
     */
    int[] findMidByRid(Integer rid);
}
