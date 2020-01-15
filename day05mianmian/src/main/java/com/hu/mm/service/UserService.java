package com.hu.mm.service;

import com.hu.mm.dao.PermissionDao;
import com.hu.mm.dao.RoleDao;
import com.hu.mm.dao.UserDao;
import com.hu.mm.pojo.Permission;
import com.hu.mm.pojo.Role;
import com.hu.mm.pojo.User;
import com.hu.pojo.utils.SqlSessionFactoryUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Project：ssm
 * Date：2019/12/31
 * Time：18:55
 * Description：TODO
 *
 * @author huxiongjun
 * @version 1.0
 */
public class UserService {

    /**
     * 用户登录
     * @param loginUser
     * @return
     * @throws IOException
     */
    public User login(User loginUser) throws IOException {
        List<String> authorityList = new ArrayList<>();
        SqlSession sqlSession = SqlSessionFactoryUtils.openSqlSession();
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        User user = userDao.findUserByUsername(loginUser);
        //认证授权
        if (user != null && user.getPassword().equals(loginUser.getPassword()) ){
            //认证成功 开始授权User Role Permission
            RoleDao roleDao = sqlSession.getMapper(RoleDao.class);
            //通过用户id 得到对应的角色列表
            List<Role> roleList = roleDao.selectRoleListByUserId(user.getId());
            if (roleList!=null && roleList.size()>0){
                PermissionDao permissionDao = sqlSession.getMapper(PermissionDao.class);
                //根据对应的角色id得到对应的权限列表
                for (Role role : roleList) {
                    authorityList.add(role.getKeyword());
                    List<Permission> permissionList = permissionDao.selectPermissionByRoleId(role.getId());
                    if (permissionList!=null && permissionList.size()>0){
                        //遍历对应的权限列表,并将权限列表添加到集合中
                        for (Permission permission : permissionList) {
                            authorityList.add(permission.getKeyword());
                        }
                    }
                }
            }
            user.setAuthorityList(authorityList);
            SqlSessionFactoryUtils.commitAndClose(sqlSession);
            return user;
        }

        SqlSessionFactoryUtils.commitAndClose(sqlSession);
        return null;

    }
}
