package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(Map<String, Integer> map);

    //分页查询
    Page<Setmeal> selectByCondition(String queryString);

    //查询所有套餐
    List<Setmeal> findAllMeal();

    //根据ID查询套餐信息
    Setmeal findBySetmealId(Integer id);

    //查询套餐数量,按套餐名分组
    List<Map<String, Object>> findSetmealCount();
    //根据ID删除套餐
    void delete(Integer id);
    //删除中间表数据
    void deleteAssociation(Integer id);

    //根据中间表检查CheckGroupId查询套餐id
    List<Setmeal> findSetmealByCheckGroupId(Integer id);

    void edit(Setmeal setmeal);
    //移动端查询单个套餐详情
    Setmeal findById(Integer id);
}
