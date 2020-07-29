package com.itheima.service;

import com.itheima.pojo.PageResult;
import com.itheima.pojo.Result;

import java.util.Map;

public interface OrderService {
    //预约服务
    Result order(Map map) throws Exception;

    //根据ID查询预约明细
    Result findById(Integer id) throws Exception;

    //预约列表分页查询
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    //确认会员到诊
    void confirmOrder(Integer id) throws RuntimeException;
}
