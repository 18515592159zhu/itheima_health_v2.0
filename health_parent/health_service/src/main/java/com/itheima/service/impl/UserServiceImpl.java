package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.*;
import com.itheima.service.UserService;
import com.itheima.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Martin MYZ
 * @create 2019-07-19-10:23
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;


    /**
     * 根据用户名查询User
     * @param username
     * @return
     */
    @Override
    public User findUserByUsername(String username) {
        //根据用户名查询User
       User user = userDao.findUserByUsername(username);
       if(user != null){
           Set<Role> roles = roleDao.findByUid(user.getId());
           //根据角色id查询角色
           if(roles !=null && roles.size() > 0 ){
               for (Role role : roles) {
                   Integer roleId = role.getId();
                   Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                   role.setPermissions(permissions);
               }
               user.setRoles(roles);
           }
       }
       return user;
    }

    @Override
    public void add(User user, Integer[] roleIds) {
        userDao.add(user);
        setUserAndRole(user.getId(),roleIds);
    }
    private void setUserAndRole(Integer id, Integer[] roleIds) {
        for (Integer roleId : roleIds) {
            Map<String,Integer> map = new HashMap<>();
            map.put("user_id",id);
            map.put("role_id",roleId);
            userDao.setUserAndRole(map);
        }
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<User> page = userDao.selectByCondition(QueryUtils.queryString(queryString));
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) {
        userDao.deleteAssociation(id);
        userDao.delete(id);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer id) {
        return userDao.findRoleIdsByUserId(id);

    }

    @Override
    public void edit(User user, Integer[] roleIds) {
        //根据套餐id删除中间表数据（清理原有关联关系）
        userDao.deleteAssociation(user.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setUserAndRole(user.getId(),roleIds);
        //更新检查组基本信息
        userDao.edit(user);
    }

    /**
     * 根据用户名修改密码
     * @param username 用户名
     * @param password 新密码
     */
    @Override
    public void chengedByUsername(String username, String password) {
        userDao.chengedByUsername(username,password);
    }

}
