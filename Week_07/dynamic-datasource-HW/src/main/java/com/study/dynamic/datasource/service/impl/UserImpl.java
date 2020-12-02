package com.study.dynamic.datasource.service.impl;

import com.study.dynamic.datasource.annotation.DataSourceTypeAdvice;
import com.study.dynamic.datasource.constant.DataSourceType;
import com.study.dynamic.datasource.dao.UserDao;
import com.study.dynamic.datasource.entity.User;
import com.study.dynamic.datasource.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userImpl")
public class UserImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    @DataSourceTypeAdvice(sourceType = DataSourceType.SLAVE1)
    public User findUserByPhone(String phone) {
        return userDao.findByPhone(phone);
    }

    @Override
    @DataSourceTypeAdvice
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public void batchSaveUsers(List<User> users) {
        userDao.saveAll(users);
    }
}
