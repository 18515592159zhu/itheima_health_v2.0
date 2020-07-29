package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.*;
import com.itheima.service.CheckItemService;
import com.itheima.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Martin MYZ
 * @create 2019-07-06-12:45
 */

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @RequestMapping("/add")
    public Result add(@RequestBody Permission permission){
        //调用业务,新增
        try {
            permissionService.add(permission);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
        return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findpage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = permissionService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        System.out.println("权限分页查询结果为:"+pageResult);
        return pageResult;
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id){
        //调用业务,进行删除
        try {
            permissionService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e){
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
        //响应
        return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
    }

    //编辑功能之回显数据(根据ID查询)
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Permission permission = permissionService.findById(id);
       Result result = null;
       if(permission == null){
           result = new Result(false, "没有查询到结果");
       }else{
            result =  new Result(true,"查询成功",permission);
       }
       return result;
    }

    //修改数据()
    @RequestMapping("/edit")
    public Result edit(@RequestBody Permission permission){

        try {
            permissionService.edit(permission);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
    }

    //检查组中动态显示全部检查项
    @RequestMapping("/findAll")
    public Result findAll(){
        List<Permission> permissionList = permissionService.findAll();
        if(permissionList != null && permissionList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS);
            result.setData(permissionList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_PERMISSION_FAIL);
    }
}
