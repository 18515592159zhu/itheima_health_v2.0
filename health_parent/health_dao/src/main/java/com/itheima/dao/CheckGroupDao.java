package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface CheckGroupDao {
    //查询
    Page<CheckGroup> selectByCondition(String queryString);

    //新增检查组功能(1,增加组)
    void add(CheckGroup checkGroup);

    //新增检查组功能(2,更新中间表)
    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void deleteAssociation(Integer id);

    void edit(CheckGroup checkGroup);

    //新增套餐,查询所有检查组
    List<CheckGroup> findAll();

    //删除检查组
    void delete(Integer id);

    //查询套餐中检查组信息
    List<CheckGroup> findCheckGroupById(Integer id);

    //通过检查项id查询到对应检查组id
    List<CheckGroup> findCheckGroupBycheckitemId(Integer checkitemIds);
}
