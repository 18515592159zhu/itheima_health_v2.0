package com.itheima.service;

import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.PageResult;

import java.util.List;

public interface CheckGroupService {

    //分页查询
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //检查组的id查询对应的套餐,并删除对应的套餐缓存
    void DelSetmealById(Integer CheckGroupId);

    //新建检查组
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    //套餐:查询所有检查组
    List<CheckGroup> findAll();

    //删除检查组
    void delete(Integer id);


}
