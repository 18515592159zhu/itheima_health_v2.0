package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {


    //预约
    void add(List<OrderSetting> orderSettingList);

    //预约人数回显
    List<Map> getOrderSettingByMonth(String date) throws Exception;

    //设置预约
    void editNumberByDate(OrderSetting orderSetting);

    //定时清理预约设置数据
    void deleteOrderSettingByToday() throws Exception;
}
