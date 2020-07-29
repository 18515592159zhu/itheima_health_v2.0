package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {

    //根据用户名查询User
    User findUserByUsername(String username);

    void add(User user);

    void setUserAndRole(Map<String, Integer> map);

    //分页查询
    Page<User> selectByCondition(String queryString);

    //查询所有套餐
    List<User> findAllMeal();

    //根据ID查询套餐信息
    User findById(Integer id);

    //查询套餐数量,按套餐名分组
    List<Map<String, Object>> findUserCount();

    //根据ID删除套餐
    void delete(Integer id);

    //删除中间表数据
    void deleteAssociation(Integer id);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(User user);

    //根据用户名修改密码
    void chengedByUsername(@Param("username") String username, @Param("password") String password);
}
