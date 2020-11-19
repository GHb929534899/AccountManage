package org.example.service;

import org.example.entity.User;
import org.example.mapper.UserManageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 该类将实现对外提供的服务方法，将在UserManageService声明的接口中，引用UserManageMapper中声明的数据库交互方法
 */
@Service
public class UserManageServiceImplement implements UserManageService {

    @Resource
    private UserManageMapper userManageMapper;

    @Override
    public User getFirstUser() {
        return userManageMapper.userGetAll().get(0);
    }

    @Override
    public List<User> getAllUser() {
        return userManageMapper.userGetAll();
    }

    @Override
    public User findUserById(int id) {
        return userManageMapper.userSelectById(id);
    }

    @Override
    public User insertUser(User user) {
        userManageMapper.userInsert(user);
        List<User> userList = userManageMapper.userGetAll();
        return userList.get(userList.size() - 1);
    }

    @Override
    public void deleteUser(int id) {
        userManageMapper.userDelete(id);
    }

    @Override
    public User updateUser(User user) {
        userManageMapper.userUpdate(user);
        return findUserById(user.getId());
    }

    @Override
    public List<User> getUserPage(int currentPage, int pageSize) {
        return userManageMapper.userSelectByPage((currentPage - 1) * pageSize, pageSize);
    }
}