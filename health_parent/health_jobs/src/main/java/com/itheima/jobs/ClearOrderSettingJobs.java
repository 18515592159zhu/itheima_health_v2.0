package com.itheima.jobs;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.service.OrderSettingService;

/**
 * 定时清理预约设置数据
 */
public class ClearOrderSettingJobs {

    @Reference
    private OrderSettingService orderSettingService;

    public void clearOrderSetting() throws Exception
    {
        System.out.println("clearOrderSetting()...");

        orderSettingService.deleteOrderSettingByToday();

    }
}
