package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemDao;
import com.itheima.dao.PermissionDao;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.PageResult;
import com.itheima.pojo.Permission;
import com.itheima.service.CheckItemService;
import com.itheima.service.PermissionService;
import com.itheima.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Martin MYZ
 * @create 2019-07-06-12:38
 */
@Service(interfaceClass= PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    //新增检查项
    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    //分页查询所有检查项
    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        //mybatis分页助手插件
        PageHelper.startPage(currentPage,pageSize);
        // 字符串模糊查询
        Page<Permission> page = permissionDao.selectByCondition(QueryUtils.queryString(queryString));
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Integer id) throws RuntimeException{
        //判断当前检查项是否被检查组引用到了
        Long count = permissionDao.findCountByPermissionId(id);
        if(count>0){
        //有引用dao,抛出异常
            throw new RuntimeException("当前权限项被引用,不能删除");
        }
        //没有引用,调用Dao, 删除
        permissionDao.deleteById(id);
    }

    @Override
    public Permission findById(Integer id) {
       return permissionDao.findById(id);

    }

    @Override
    public void edit(Permission permission) {
        permissionDao.update(permission);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
