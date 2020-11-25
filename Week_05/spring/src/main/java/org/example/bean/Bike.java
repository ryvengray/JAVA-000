package org.example.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Bike implements Vehicle {

    @Override
    public void run() {
        log.info("Bike run");
    }
}
