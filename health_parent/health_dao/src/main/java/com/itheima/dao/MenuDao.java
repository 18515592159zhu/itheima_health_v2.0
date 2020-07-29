package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuDao {
    //根据角色ID查询对应所有1级菜单,
    Menu findMenuInfo(Integer rid);

    //查询二级菜单,根据父菜单ID
    List<Menu> findSecondMenuInfo(Integer id);
    
    
    //********************菜单管理增删改查[开始]********************************    
    /*持久层的接口*/
    public void add(Menu menu);

    //分页查询
    Page<Menu> selectByCondition(String queryString);


    //删除
    void deleteById(Integer id);
    //检查所选菜单项是否被检查组所引用
    Long findCountByMenuId(Integer id);

    //回显数据,(根据ID查询)
    Menu findById(Integer id);

    //修改数据
    void update(Menu menu);

    //角色组动态显示所有菜单项
    List<Menu> findAll();

    //根据中间表查询菜单项
    List<Menu> findMenuById(Integer id);
    
    
    
    //********************菜单管理增删改查[结束]********************************


}
