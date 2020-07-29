package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.dao.RoleDao;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Menu;
import com.itheima.pojo.PageResult;
import com.itheima.service.MenuService;
import com.itheima.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Martin MYZ
 * @create 2019-07-21-20:07
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleDao roleDao;
    /**
     * 获得所有菜单
     * @return
     */
    @Override
    public List<Menu> getMenuInfo(String username) {
        //1.查询对应用户名的所有1级菜单,用户对应多角色
        Integer[] rids = roleDao.findRidByUsername(username);
        //2.根据角色ids,获得关联的菜单id,存入到set集合中,去掉重复的菜单界面
        Set<Integer> set = new LinkedHashSet<>();
        for (Integer rid : rids) {
            //2.1根据角色id查询关联的菜单id
            int[]  mid =  roleDao.findMidByRid(rid);
            for (int id : mid) {
                set.add(id);
            }
        }
        List<Menu> menuList = new ArrayList<>();
        for (int rid : set) {
            //2.2根据菜单id,获得一级菜单的集合
            Menu menuInfo = menuDao.findMenuInfo(rid);
            if (menuInfo != null){
                menuList.add(menuInfo);
            }
        }
        for (Menu menu : menuList) {
            //2.3遍历一级菜单集合,根据菜单对象id,获得二级菜单集合
            List<Menu> list2 = menuDao.findSecondMenuInfo(menu.getId());
            menu.setChildren(list2);
        }
       return menuList;
    }
    
    //************************菜单管理增删改查[起始]*************************************************
    //新增菜单项
    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    //分页查询所有菜单项
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //mybatis分页助手插件
        PageHelper.startPage(currentPage,pageSize);
        Page<Menu> page = menuDao.selectByCondition(QueryUtils.queryString(queryString));
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) throws RuntimeException{
        //判断当前菜单项是否被检查组引用到了
        Long count = menuDao.findCountByMenuId(id);
        if(count>0){
            //有引用dao,抛出异常
            throw new RuntimeException("当前菜单项被引用,不能删除");
        }
        //没有引用,调用Dao, 删除
        menuDao.deleteById(id);
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);

    }

    @Override
    public void edit(Menu menu) {
        menuDao.update(menu);
    }

    //************************菜单管理增删改查[结束]*************************************************


    @Override
    public List<Menu> findAll() {
        return menuDao.findAll();
    }
    
}
