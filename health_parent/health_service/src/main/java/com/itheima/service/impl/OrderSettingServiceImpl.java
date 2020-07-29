package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Martin MYZ
 * @create 2019-07-15-20:45
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;


    @Override
    public void add(List<OrderSetting> orderSettingList){
        if(orderSettingList != null && orderSettingList.size() > 0){
            for (OrderSetting orderSetting : orderSettingList) {
                //检查此数据是否存在
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(count > 0){
                    //已存在
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    @Override
    public List<Map> getOrderSettingByMonth(String date) throws Exception {
        String startDate = date + "-1";
        String endDate = date + "-31";
        Map map = new HashMap();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> orderSettingList = new ArrayList<>();

            for (OrderSetting orderSetting : list) {
                Map orderSettingMap = new HashMap();
                orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
                orderSettingMap.put("number", orderSetting.getNumber());
                orderSettingMap.put("reservations",orderSetting.getReservations());
                orderSettingList.add(orderSettingMap);
            }
        return orderSettingList;
    }


    //设置预约
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count>0){
            //当前日期,已经进行预约设置,需要进行修改操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //当前日期未进行设置,进行添加操作
            orderSettingDao.add(orderSetting);
        }
    }

    //定时清理预约设置数据
    @Override
    public void deleteOrderSettingByToday() throws Exception
    {
        String date = DateUtils.parseDate2String(new Date());
        orderSettingDao.deleteOrderSettingByToday(date);
    }
}
