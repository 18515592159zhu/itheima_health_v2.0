package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    //根据条件查询预约
    List<Order> findByCondition(Order order);

    //添加预约
    void add(Order order);

    //根据预约id查询预约信息，包括体检人信息、套餐信息
    Map findById4Detail(Integer id);

    //根据日期查询预约数
    Integer findOrderCountByDate(String date);

    //查询日期后的预约数
    Integer findOrderCountAfterDate(String date);

    //根据日期查询到诊数
    Integer findVisitsCountByDate(String date);
    //根据日期查询日期后到诊数
    Integer findVisitsCountAfterDate(String date);

    //热门套餐,根据预约数量倒序排序,取前四
    List<Map> findHotSetmeal();

    //*****************预约列表增删改查*************
    //预约列表分页展示
    Page<Map<String, Object>> selectByCondition(String queryString);

    /**
     * 确认预约到诊状态
     * @param id
     */
    void confirmOrderStatus(Integer id);
}
