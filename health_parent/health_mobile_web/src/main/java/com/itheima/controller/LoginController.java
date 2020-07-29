package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.constants.RedisMessageConstant;
import com.itheima.pojo.Member;
import com.itheima.pojo.Result;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-18-15:50
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Reference
    private MemberService memberService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){

        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //验证码校验
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if(codeInRedis !=validateCode || codeInRedis == null){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }else{
            //验证码输入正确
            //判断是否是会员
          Member member =  memberService.findByTelephone(telephone);
          if(member == null){
              //注册新会员
              member = new Member();
              member.setPhoneNumber(telephone);
              member.setRegTime(new Date());
              memberService.add(member);
          }
        }
        //手机号查会员
        //Cookie cookie = new Cookie();

        return null;
    }
}
