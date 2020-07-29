package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.*;
import com.itheima.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Martin MYZ
 * @create 2019-07-21-20:03
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    /**
     * 加载所有一二级菜单,前端界面传送username,查询返回查单有bug,此方法启用
     * @param
     * @return
     */
   /* @RequestMapping("/getMenuInfo")
    public Result getMenuInfo(String username){

        try {
            List<Menu> list =  menuService.getMenuInfo(username);
            System.out.println(list);
            return new Result(true, MessageConstant.GET_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
    }*/

//*********************菜单管理中增删改查(起)*****************************************************//

    @RequestMapping("/add")
    public Result add(@RequestBody Menu menu){
        //调用业务,新增
        try {
            menuService.add(menu);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
        return new Result(true,MessageConstant.ADD_MENU_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findpage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = menuService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(Integer id){
        //调用业务,进行删除
        try {
            menuService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e){
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
        //响应
        return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
    }

    //编辑功能之回显数据(根据ID查询)
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Menu menu = menuService.findById(id);
        Result result = null;
        if(menu == null){
            result = new Result(false, "没有查询到结果");
        }else{
            result =  new Result(true,"查询成功",menu);
        }
        return result;
    }

    //修改数据()
    @RequestMapping("/edit")
    public Result edit(@RequestBody Menu menu){

        try {
            menuService.edit(menu);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
    }
    //********************菜单管理中增删改查(尾)*****************************************************//
    //角色中动态显示全部菜单项
    @RequestMapping("/findAll")
    public Result findAll(){
        List<Menu> menuList = menuService.findAll();
        if(menuList != null && menuList.size() > 0){
            Result result = new Result(true, MessageConstant.QUERY_MENU_SUCCESS);
            result.setData(menuList);
            return result;
        }
        return new Result(false,MessageConstant.QUERY_MENU_FAIL);
    }
}
