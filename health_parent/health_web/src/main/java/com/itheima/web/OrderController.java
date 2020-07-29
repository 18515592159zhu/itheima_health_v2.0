package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.QueryPageBean;
import com.itheima.pojo.Result;
import com.itheima.service.CheckItemService;
import com.itheima.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

   /* @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        //调用业务,新增
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }*/

    //分页查询预约列表
    @RequestMapping("/findOrderList")
//    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public PageResult findOrderList(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = orderService.pageQuery(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }




   /* //删除
    @RequestMapping("/delete")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    public Result delete(Integer id){
        //调用业务,进行删除
        try {
            checkItemService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e){
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        //响应
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //编辑功能之回显数据(根据ID查询)
    @RequestMapping("/findById")
    public Result findById(Integer id){
       CheckItem checkItem = checkItemService.findById(id);
       Result result = null;
       if(checkItem == null){
           result = new Result(false, "没有查询到结果");
       }else{
            result =  new Result(true,"查询成功",checkItem);
       }
       return result;
    }


*/
   //修改数据()
   @RequestMapping("/confirmOrder")
   public Result confirmOrder(Integer id){
       try {
           orderService.confirmOrder(id);
       } catch (Exception e) {
           return new Result(false, MessageConstant.CONFIRM_ORDER_STATUS_FAIL);
       }
       return new Result(true, MessageConstant.CONFIRM_ORDER_STATUS_SUCCESS);
   }
}
