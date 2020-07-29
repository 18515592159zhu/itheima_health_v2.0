package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constants.MessageConstant;
import com.itheima.constants.RedisConstant;
import com.itheima.constants.RedisMessageConstant;
import com.itheima.pojo.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @author Martin MYZ
 * @create 2019-07-17-19:51
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
     private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order( String telephone){
        /*//生成验证码前,查看有无验证码,是否有效
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if(codeInRedis !=null && codeInRedis.length()>0){

        }*/


        Integer code = ValidateCodeUtils.generateValidateCode(6);

        try {
            //生成验证码
            //发送短信验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        System.out.println("生成的验证码为:"+ code);

        //存入redis中
        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result send4Login( String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(6);

        try {
            //生成验证码
            //发送短信验证码
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }

        System.out.println("生成的登录验证码为:"+ code);

        //存入redis中
        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,5*60,code.toString());
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

}
