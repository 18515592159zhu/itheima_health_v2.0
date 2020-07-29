package com.itheima.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Result;
import com.itheima.service.OrderSettingService;
import com.itheima.constants.MessageConstant;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-15-20:32
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * Excel文件上传，并解析文件内容保存到数据库
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam MultipartFile excelFile){
        try {
            //读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingList  = new ArrayList<>();
            if(list != null && list.size() > 0 ){
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]),Integer.parseInt(strings[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.add(orderSettingList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

   @RequestMapping("/getOrderSettingByMonth")
   public Result getOrderSettingByMonth(String date){
       try {
           List<Map> list = orderSettingService.getOrderSettingByMonth(date);
           //System.out.println("预约list返回结果为:"+ list);
           //成功
           return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
       } catch (Exception e) {
           e.printStackTrace();
           //失败
           return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
       }
   }

    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
