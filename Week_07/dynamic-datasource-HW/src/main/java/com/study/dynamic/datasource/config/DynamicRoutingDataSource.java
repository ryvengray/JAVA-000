package com.study.dynamic.datasource.config;

import com.study.dynamic.datasource.constant.DataSourceType;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;


public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static final Logger log = LoggerFactory.getLogger(DynamicRoutingDataSource.class);

    public DynamicRoutingDataSource(HikariDataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
        // 设置默认数据源
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        // 设置多数据源. key value的形式
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getDataSource();
    }


    public static void setDataSource(DataSourceType dataSource) {
        log.info("切换到{}数据源", dataSource);
        contextHolder.set(dataSource);
    }


    public static DataSourceType getDataSource() {
        return contextHolder.get();
    }


    public static void clearDataSource() {
        contextHolder.remove();
    }
}
