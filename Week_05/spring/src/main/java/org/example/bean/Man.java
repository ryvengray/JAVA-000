package org.example.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class Man {

    @Resource
    private Car car;

    @Autowired
    private Apple apple;

    @Autowired
    private Vehicle bike;

    @Resource(name = "motorbike")
    private Vehicle mBike;


    public void active() {
        log.info("Man active");
        car.run();
        apple.eat();
        bike.run();
        mBike.run();
    }
}
