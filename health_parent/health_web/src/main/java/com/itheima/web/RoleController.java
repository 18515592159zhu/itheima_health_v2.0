package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.*;
import com.itheima.service.CheckGroupService;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Martin MYZ
 * @create 2019-07-10-9:13
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;


    //新增角色功能
    @RequestMapping("/add")
    public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds){
        try {
            System.out.println("选中权限ID为"+permissionIds.toString());
            System.out.println("选中菜单ID为"+menuIds.toString());
            roleService.add(role,permissionIds,menuIds);
        } catch (Exception e) {
            //新增失败
            return new Result(false,MessageConstant.ADD_ROLE_FAIL);
        }
        //新增成功
        return new Result(true,MessageConstant.ADD_ROLE_SUCCESS);

    }

    //分页查询角色功能
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        //调用业务,查询
        PageResult pageResult = roleService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        //响应
        return pageResult;
    }


    //编辑角色
    //根据id查询
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Role role = roleService.findById(id);
        if(role != null){
            Result result = new Result(true, MessageConstant.QUERY_ROLE_SUCCESS);
            result.setData(role);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
    }

    //根据角色合id查询对应的所有权限项id
    @RequestMapping("/findPermissionIdsByRoleId")
    public List<Integer> findPermissionIdsByRoleId(Integer id){
        List<Integer> list = roleService.findPermissionIdsByRoleId(id);
        return list;
    }
    //根据角色合id查询对应的所有菜单项id
    @RequestMapping("/findMenuIdsByRoleId")
    public List<Integer> findMenuIdsByRoleId(Integer id){
        List<Integer> list = roleService.findMenuIdsByRoleId(id);
        return list;
    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody Role role,Integer[] permissionIds, Integer[] menuIds){
        try {
            roleService.edit(role,permissionIds,menuIds);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_ROLE_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_ROLE_SUCCESS);
    }

    //删除角色
    @RequestMapping("/delete")
    public Result delete(Integer id){
        //调用业务,进行删除
        try {
            roleService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e){
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
        //响应
        return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
    }





    //新增套餐功能-查看所有角色

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll(){
        List<Role> roleList = roleService.findAll();
        if(roleList != null && roleList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_ROLE_SUCCESS);
            result.setData(roleList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
    }
}
