package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.SetmealDao;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.constants.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Martin MYZ
 * @create 2019-07-14-12:51
 */
@Transactional
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private JedisPool jedisPool;

    //用于设置redis缓存的存活时间
    private static final  int REDISE_EXIST_TIME = 20;  //单位分钟

    /**
     * 用于删除指定套餐id的redis
     */
    public void deleMealRedis(Integer idm){
        jedisPool.getResource().del("Setmeal:" + idm);
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if(checkgroupIds != null && checkgroupIds.length > 0){
            //绑定套餐和检查组的多对多关系
            setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        }//将图片名称保存到Redis
        savePic2Redis(setmeal.getImg());
        jedisPool.getResource().del("List<Setmeal>");  //清除List<Setmeal> 套餐列表缓存
    }

    //将图片名称保存到Redis
    private void savePic2Redis(String pic){
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }


    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        for (Integer checkgroupId : checkgroupIds) {
            Map<String,Integer> map = new HashMap<>();
            map.put("setmeal_id",id);
            map.put("checkgroup_id",checkgroupId);
            setmealDao.setSetmealAndCheckGroup(map);
        }
    }


    //分页查询
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 查询所有套餐组合
     * @return
     */
    @Override
    public List<Setmeal> findAllMeal() {
        if(null == jedisPool.getResource().get("List<Setmeal>")){ //判断redis里是否有key为"List<Setmeal>"的值
            List<Setmeal> allMeal = setmealDao.findAllMeal();
            //设置缓存时长为10分钟，避免有更新的套餐无法拉取
            jedisPool.getResource().setex("List<Setmeal>",REDISE_EXIST_TIME*60,JSON.toJSONString(allMeal));
            return allMeal;
        }
        String list = jedisPool.getResource().get("List<Setmeal>");//获取redis中的key为"List<Setmeal>"的缓存
        List<Setmeal> resultList = JSON.parseArray(list, Setmeal.class);  //将缓存中的字符串转换成List
        return resultList;
    }

    /**
     * 根据ID查询单个套餐详情
     * @param id
     * @return
     */
    @Override
    public Setmeal findBySetmealId(Integer id) {
        return setmealDao.findBySetmealId(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
       return  setmealDao.findSetmealCount();
    }

    //根据ID删除套餐
    @Override
    public void delete(Integer id) {
        setmealDao.delete(id);
        setmealDao.deleteAssociation(id);
        jedisPool.getResource().del("List<Setmeal>");  //清除List<Setmeal> 套餐列表缓存
    }

    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(Integer id) {
        return null;
    }


    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //根据套餐id删除中间表数据（清理原有关联关系）
        setmealDao.deleteAssociation(setmeal.getId());
        //向中间表(t_checkgroup_checkitem)插入数据（建立检查组和检查项关联关系）
        setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        //更新检查组基本信息
        setmealDao.edit(setmeal);
        jedisPool.getResource().del("List<Setmeal>");  //清除List<Setmeal> 套餐列表缓存
        jedisPool.getResource().del("Setmeal:" + setmeal.getId());  //清除对应id套餐详情的缓存
    }

    //手机端查询单个套餐详情
    @Override
    public Setmeal findById(Integer idm) {
        if(null == jedisPool.getResource().get("Setmeal:" + idm)){ //判断redis里是否有key为"List<Setmeal>"的值
            Setmeal meal = setmealDao.findById(idm);
            //设置缓存时长为20分钟，避免有更新的套餐无法拉取
            jedisPool.getResource().setex("Setmeal:" + idm,REDISE_EXIST_TIME*60,JSON.toJSONString(meal));
            return meal;
        }
        return setmealDao.findById(idm);
    }
}
