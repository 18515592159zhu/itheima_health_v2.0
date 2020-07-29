package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
public interface OrderSettingDao {

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    //查询当月预约情况
    List<OrderSetting> getOrderSettingByMonth(Map map);

    //根据日期查询预约设置
    OrderSetting findByOrderDate(Date date);

    //根据日期获得当前预约人数
    int getReservation(Date date);

    //根据日期获得预约设置上限人数
    int getNumber(Date date);

    //根据预约日期更新预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);

    //定时清理预约设置数据
    void deleteOrderSettingByToday(String date);

}
