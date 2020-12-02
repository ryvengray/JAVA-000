package com.study.dynamic.datasource.constant;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum DataSourceType {
    /**
     * 主库
     */
    MASTER,

    /**
     * 从库1
     */
    SLAVE1,

    /**
     * 从库2
     */
    SLAVE2;

    static final List<DataSourceType> list = new ArrayList<>();

    static {
        list.add(SLAVE1);
        list.add(SLAVE2);
    }

    public static DataSourceType getRandomSlaveType() {
        int next = ThreadLocalRandom.current().nextInt(0, list.size());
        return list.get(next);
    }
}
