package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constants.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.*;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import com.itheima.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-17-21:43
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    public Result order(Map map) throws Exception {
        //判断日期是否可以预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        if(orderSetting == null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //判断当前日期是否预约已满
        int reservationNum = orderSettingDao.getReservation(date);
        int number = orderSettingDao.getNumber(date);
        if(reservationNum >=number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        //判断是否是会员
        String telephone = (String)map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        //
        if(member !=null){
            //是会员, 避免重复预约
            //3.1.是会员, 避免重复预约
            Integer memberId = member.getId();
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order = new Order(memberId,date,null,null,setmealId);
            List<Order> list = orderDao.findByCondition(order);
            if(list != null && list.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else{
            //3.2.不是会员, 需要添加到会员表
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }
        //进行预约,预约人数+1
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        //保存预约信息到预约表
        Order order = new Order(member.getId(),date,(String)map.get("orderType"),
                Order.ORDERSTATUS_NO,Integer.parseInt((String)map.get("setmealId")));
        orderDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);

    }

    @Override
    public Result findById(Integer id) throws Exception {

        Map map = orderDao.findById4Detail(id);
        if(map!=null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate", DateUtils.parseDate2String(orderDate));
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,map);
        }
        return new Result(false,MessageConstant.QUERY_ORDER_FAIL);

    }


    //***********预约列表[增删改查]*******************

    /**
     * 预约列表分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //mybatis分页助手插件
        PageHelper.startPage(currentPage,pageSize);

        Page<Map<String,Object>> page = orderDao.selectByCondition(QueryUtils.queryString(queryString));
        return new PageResult(page.getTotal(),page.getResult());

    }

    /**
     * 确认会员到诊
     * @param id
     */
    @Override
    public void confirmOrder(Integer id) throws RuntimeException {
        if(id ==null || id==0 ){
            throw new RuntimeException("会员不存在");
        }
        orderDao.confirmOrderStatus(id);
    }
}
