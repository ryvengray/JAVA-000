package com.study.dynamic.datasource.service;

import com.study.dynamic.datasource.entity.User;

import java.util.List;

public interface IUserService {
    User findUserByPhone(String phone);

    void saveUser(User user);

    void batchSaveUsers(List<User> users);
}
