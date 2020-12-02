package com.study.dynamic.datasource.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lfy
 * @description
 * @date 2020-08-16
 */
@Data
@Component
@ConfigurationProperties(prefix = "snow", ignoreUnknownFields = false, ignoreInvalidFields = true)
public class SnowFlakeConfig {
    private Long workerId;
    private Long dataCenterId;
}
