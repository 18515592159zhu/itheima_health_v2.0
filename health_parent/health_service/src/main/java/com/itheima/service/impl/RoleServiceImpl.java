package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.pojo.Role;
import com.itheima.pojo.PageResult;
import com.itheima.service.RoleService;
import com.itheima.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-10-9:19
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;


    //新增角色功能
    @Override
    public void add(Role role, Integer[] permissionIds, Integer[] menuIds) {
        roleDao.add(role);
        setRoleAndPermission(role.getId(),permissionIds);
        setRoleAndMenu(role.getId(),menuIds);
    }

    //向role_checkItem中间表中添加
    private void setRoleAndPermission(Integer roleId, Integer[] permissionIds) {
        if(permissionIds != null && permissionIds.length>0){
            Map<String, Integer> map = new HashMap<>();
            for (Integer permissionId : permissionIds) {
                map.put("role_id", roleId);
                map.put("permission_id", permissionId);
                roleDao.setRoleAndPermission(map);
            }
        }
    }
    //向role_checkItem中间表中添加
    private void setRoleAndMenu(Integer roleId, Integer[] menuIds) {
        if(menuIds != null && menuIds.length>0){
            Map<String, Integer> map = new HashMap<>();
            for (Integer menuId : menuIds) {
                map.put("role_id", roleId);
                map.put("menu_id", menuId);
                roleDao.setRoleAndMenu(map);
            }
        }
    }


    @Override
    //分页查询
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Role> page =  roleDao.selectByCondition(QueryUtils.queryString(queryString));
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer id) {
        return roleDao.findPermissionIdsByRoleId(id);
    }
    @Override
    public List<Integer> findMenuIdsByRoleId(Integer id) {
        return roleDao.findMenuIdsByRoleId(id);
    }

    @Override
    //编辑角色，同时需要更新和权限的关联关系
    public void edit(Role role, Integer[] permissionIds, Integer[] menuIds) {
        //根据角色id删除中间表数据（清理原有关联关系）
        roleDao.deletePermissionAssociation(role.getId());
        roleDao.deleteMenuAssociation(role.getId());
        //向中间表(t_role_permission)插入数据（建立角色和权限关联关系）
        setRoleAndPermission(role.getId(),permissionIds);
        setRoleAndMenu(role.getId(),menuIds);
        //更新角色基本信息
        roleDao.edit(role);
    }


    //新增套餐查询所有角色
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void delete(Integer id) {
        roleDao.deletePermissionAssociation(id);
        roleDao.deleteMenuAssociation(id);
        roleDao.delete(id);
    }

}
