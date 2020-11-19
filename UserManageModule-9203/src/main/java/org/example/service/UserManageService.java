package org.example.service;

import org.example.entity.User;

import java.util.List;

/**
 * 该接口将声明对外提供的服务方法
 */
public interface UserManageService {

    User getFirstUser();

    List<User> getAllUser();

    User findUserById(int id);

    User insertUser(User user);

    void deleteUser(int id);

    User updateUser(User user);

    List<User> getUserPage(int currentPage,int pageSize);
}