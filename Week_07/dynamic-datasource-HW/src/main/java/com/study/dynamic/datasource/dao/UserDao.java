package com.study.dynamic.datasource.dao;

import com.study.dynamic.datasource.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDao extends JpaRepository<User, Long> {
    User findByPhone(String phone);
}
