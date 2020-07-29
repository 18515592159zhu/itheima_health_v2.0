package com.itheima.test;

import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-16-12:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-dao.xml")
public class OrdersettingDaoTest {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Test
    public void fun01() throws ParseException {
        Map map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parse1 = sdf.parse("2019-07-01");
        Date parse2 = sdf.parse("2019-07-31");

        map.put("dataBegin","2019-07-01");
        map.put("dataEnd","2019-07-31");
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        System.out.println("list"+list);

        orderSettingDao.findCountByOrderDate(parse1);
    }
}
