package com.study.dynamic.datasource.service;

import com.google.common.base.Stopwatch;
import com.study.dynamic.datasource.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class UserInsertTest {
    @Resource(name = "userImpl")
    private IUserService userService;

    @Test
    public void insertUser(){
        List<User>  users  = getInitData(1);
        StopWatch stopWatch = StopWatch.createStarted();
        for (int i = 0; i < users.size(); i++) {
            userService.saveUser(users.get(i));
        }
        stopWatch.stop();
        log.info("Single insert cost: "+ stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    @Test
    public void batchIsertUser(){
        List<User>  users  = getInitData(200000);
        StopWatch stopWatch = StopWatch.createStarted();
        userService.batchSaveUsers(users);
        stopWatch.stop();
        log.info("Single insert cost: "+ stopWatch.getTime(TimeUnit.MILLISECONDS));
    }

    private List<User> getInitData(int init){
        List<User>  users= new ArrayList<>(100000);
        for (int i = 0; i < 100000; i++) {
            User user = new User();
            user.setName("A"+i+init);
            user.setPhone(String.valueOf(i+init));
            users.add(user);
        }
        return users;
    }

}
