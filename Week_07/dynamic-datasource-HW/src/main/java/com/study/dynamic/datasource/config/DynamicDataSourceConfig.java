package com.study.dynamic.datasource.config;

import com.google.common.collect.Maps;
import com.study.dynamic.datasource.constant.DataSourceType;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import java.util.Map;

@Configuration
public class DynamicDataSourceConfig {


    @Bean(name = "rwDataSource")
    @ConfigurationProperties("spring.datasource.rw")
    public HikariDataSource rwDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "rd1DataSource")
    @ConfigurationProperties("spring.datasource.rd1")
    public HikariDataSource rd1DataSource() {
        return new HikariDataSource();
    }


    @Bean(name = "rd2DataSource")
    @ConfigurationProperties("spring.datasource.rd2")
    public HikariDataSource rd2DataSource() {
        return new HikariDataSource();
    }

    @Bean
    @Primary
    @DependsOn({"rwDataSource", "rd1DataSource", "rd2DataSource"})
    public DynamicRoutingDataSource dataSource() {
        Map<Object, Object> targetDataSources = Maps.newHashMapWithExpectedSize(3);
        // 每个key对应一个数据源
        targetDataSources.put(DataSourceType.MASTER, rwDataSource());
        targetDataSources.put(DataSourceType.SLAVE1, rd1DataSource());
        targetDataSources.put(DataSourceType.SLAVE2, rd2DataSource());
        return new DynamicRoutingDataSource(rwDataSource(), targetDataSources);
    }

}
