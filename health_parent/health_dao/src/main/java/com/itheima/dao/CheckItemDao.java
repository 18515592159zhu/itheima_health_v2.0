package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface CheckItemDao {

    /*持久层的接口*/
    public void add(CheckItem checkItem);

    //分页查询
    Page<CheckItem> selectByCondition(String queryString);


    //删除
    void deleteById(Integer id);
    //检查所选检查项是否被检查组所引用
    Long findCountByCheckItemId(Integer id);

    //回显数据,(根据ID查询)
    CheckItem findById(Integer id);

    //修改数据
    void update(CheckItem checkItem);

    //检查组动态显示所有检查项
    List<CheckItem> findAll();

    //根据中间表查询检查项
    List<CheckItem> findCheckItemById(Integer id);


}
