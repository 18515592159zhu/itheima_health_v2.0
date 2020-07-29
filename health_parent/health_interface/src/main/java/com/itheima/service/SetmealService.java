package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    //套餐服务接口
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    //分页查询
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //查询所有套餐
    List<Setmeal> findAllMeal();

    Setmeal findBySetmealId(Integer id);

    //查询套餐总数,按套餐名分组
    List<Map<String, Object>> findSetmealCount();

    //删除套餐(id)
    void delete(Integer id);

    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);


    //移动端根据ID查询套餐详情
    Setmeal findById(Integer id);
}
