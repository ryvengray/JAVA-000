package org.example.bean;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Car implements Vehicle {

    private String name;

    private Tyre tyre;

    @Override
    public void run() {
        log.info("Car run");
        tyre.roll();
    }
}
