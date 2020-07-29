package com.itheima.jobs;

import com.itheima.utils.QiniuUtils;
import com.itheima.constants.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author Martin MYZ
 * @create 2019-07-15-10:40
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    //清理图片
    public void clearImg(){
        System.out.println("clearImg()...");
        //1.计算set的差值
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //2.遍历集合
        for (String pic : set) {
            //3.删除
            //3.1 删除七牛空间上的
            QiniuUtils.deleteFileFromQiniu(pic);
            //3.2删除redis集合里面的
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
        }
    }
}
