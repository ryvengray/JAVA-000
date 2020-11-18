package org.example.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Motorbike implements Vehicle {

    @Override
    public void run() {
        log.info("MotorBike run");
    }
}
