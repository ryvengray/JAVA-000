package com.study.dynamic.datasource.shardingsphere.service.impl;

import com.study.dynamic.datasource.dao.UserDao;
import com.study.dynamic.datasource.entity.User;
import com.study.dynamic.datasource.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userShardingImpl")
public class UserShardingImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void batchSaveUsers(List<User> users) {
        userDao.saveAll(users);
    }
}
