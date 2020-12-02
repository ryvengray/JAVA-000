package com.study.dynamic.datasource.service;

import com.alibaba.fastjson.JSON;
import com.study.dynamic.datasource.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserServiceTest {
    @Resource(name = "userImpl")
    private IUserService userService;

    @Test
    public void findUserByPhone() {
        String phone = "12345678";
        User userByPhone = userService.findUserByPhone(phone);
        log.info(JSON.toJSONString(userByPhone));
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setName("aa");
        user.setPhone("12345678");
        userService.saveUser(user);
    }
}
