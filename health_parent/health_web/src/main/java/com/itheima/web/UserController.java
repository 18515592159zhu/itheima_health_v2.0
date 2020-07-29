package com.itheima.web;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.*;
import com.itheima.service.MenuService;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-20-21:59
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @Reference
    private MenuService menuService;

    /**
     * 返回用户名,加载所有一二级菜单
     *
     * @return
     * @throws Exception
     */
    //查询用户名
    @RequestMapping("/getUsername")
    public Result getUsername() throws Exception {
        try {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //1,根据登录用户名
            String username = user.getUsername();
            Map<String, Object> menuMap = new HashMap<>();//封装返回值
            //2.根据用户名查询用户角色和菜单信息
            List<Menu> menuList = menuService.getMenuInfo(username);
            //3.判断权限控制的菜单是否为空,
            if (menuList != null && menuList.size() > 0) {
                Menu menu1 = menuList.get(0);
                List<Menu> children = menu1.getChildren();
                Menu menu2 = children.get(0);
                String linkUrl = menu2.getLinkUrl();
                //不为空就获取有权限菜单的第一个界面
                if (!("".equals(linkUrl)) && linkUrl != null) {
                    menuMap.put("firstpage", linkUrl);
                } else {
                    menuMap.put("firstpage", null);
                }
            }
            menuMap.put("username", username);
            //3.判断权限集合为空或null时,返回null
            if (menuList == null || menuList.size() == 0) {
                menuMap.put("menuList", null);
            } else {
                menuMap.put("menuList", menuList);
            }
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, menuMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /*@RequestMapping("/changePassword")
    public Result changePassword() throws Exception{
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true,MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }

    }*/
    //********************用户增删改查[开始]************************

    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody User user, Integer[] roleIds) {
        try {
            userService.add(user, roleIds);
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            //jedisPool.getResource().sadd(RedisConstant.USER_PIC_DB_RESOURCES);
        } catch (Exception e) {
            //新增套餐失败
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
        //新增套餐成功
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);
    }


    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = userService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString()
        );
        return pageResult;
    }

    //删除检查组
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        //调用业务,进行删除
        try {
            userService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
        //响应
        return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
    }

    //编辑检查组
    //根据id查询
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        User user = userService.findById(id);
        if (user != null) {
            Result result = new Result(true, MessageConstant.QUERY_USER_SUCCESS);
            result.setData(user);
            return result;
        }
        return new Result(false, MessageConstant.QUERY_USER_FAIL);
    }

    //根据检查组合id查询对应的所有检查项id
    @RequestMapping("/findRoleIdsByUserId")
    public List<Integer> findRoleIdsByUserId(Integer id) {
        List<Integer> list = userService.findRoleIdsByUserId(id);
        return list;
    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody User user, Integer[] roleIds) {
        try {
            userService.edit(user, roleIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
    }


    //********************用户增删改查[结束]************************

    /**
     * 根据账户修改密码
     *
     * @param principal
     * @param pass
     * @return
     */
    @RequestMapping("chengedByUsername")
    public Result chengedByUsername(Principal principal, @RequestParam("password") String pass) {
        try {
            String username = principal.getName();
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = encoder.encode(pass);
            userService.chengedByUsername(username, password);
            return new Result(true, "修改密码成功,请记好新密码:" + pass);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改密码失败");
        }
    }
}
