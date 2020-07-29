package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.CheckItemDao;
import com.itheima.dao.SetmealDao;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Setmeal;
import com.itheima.service.CheckGroupService;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author Martin MYZ
 * @create 2019-07-06-12:38
 */
@Service(interfaceClass= CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Autowired
    private CheckGroupDao checkGroupDao;

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private CheckGroupService checkGroupService;

    /**
     * 先通过检查项id查询对应检查组
     * 再检查组的id查询对应的套餐,并删除对应的套餐缓存
     */
    public void DelSetmealById(Integer checkitemIds){
        List<CheckGroup> checkGroupList = checkGroupDao.findCheckGroupBycheckitemId(checkitemIds);
        System.out.println("checkGroupList::"+checkGroupList);
        for (CheckGroup checkGroup : checkGroupList) {
            checkGroupService.DelSetmealById(checkGroup.getId());
        }
    }

    //新增检查项
    @Override
    public void add(CheckItem checkItem) {
        System.out.println("CheckItemServiceImpl..add()...");
        checkItemDao.add(checkItem);
        //DelSetmealById(checkItem.getId());  //清除redis缓存 ，目前不会触发逻辑
    }

    //分页查询所有检查项
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //mybatis分页助手插件
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) throws RuntimeException{
        //判断当前检查项是否被检查组引用到了
        Long count = checkItemDao.findCountByCheckItemId(id);
        if(count>0){
        //有引用dao,抛出异常
            throw new RuntimeException("当前检查项被引用,不能删除");
        }
        //没有引用,调用Dao, 删除
        checkItemDao.deleteById(id);

        //DelSetmealById(id);  //清除redis缓存  删除有绑定检查组逻辑，目前不会触发
    }

    @Override
    public CheckItem findById(Integer id) {
       return checkItemDao.findById(id);

    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.update(checkItem);
        DelSetmealById(checkItem.getId());  //清除redis缓存
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
