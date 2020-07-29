package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constants.MessageConstant;
import com.itheima.constants.RedisMessageConstant;
import com.itheima.pojo.Order;
import com.itheima.pojo.Result;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-17-20:40
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/submit")
    public Result submitOrder(@RequestBody Map map){
        //验证码5分钟内有效,不能重复发送


        String telephone = (String)map.get("telephone");
        //获得redis中的验证码
        String codeinRedis = jedisPool.getResource().get(telephone+RedisMessageConstant.SENDTYPE_ORDER);
        //获得前端传来的验证码
        String code = (String)map.get("validateCode");
        //比较验证码
        if(codeinRedis ==null || !code.equals(codeinRedis) ){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //调用业务,进行预约,响应
        Result result = null;

        //封装预约类型,
        try {
            map.put("orderType",Order.ORDERTYPE_WEIXIN);
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        if(result.isFlag()){
            try {
                //预约成功,发送通知短信
                String orderDate= (String)map.get("orderDate");
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);

                //预约成功,验证码作废
                jedisPool.getResource().del(telephone+RedisMessageConstant.SENDTYPE_ORDER);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Result result = null;
        try {
            result = orderService.findById(id);
            //查询成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,result.getData());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
