package org.example.springb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 描述：配置类
 **/
@Configuration
@EnableConfigurationProperties(StudentProperties.class)
@ConditionalOnProperty(
        prefix = "student",
        name = "enable",
        havingValue = "true"
)
public class StudentConfig {
    @Autowired
    private StudentProperties studentProperties;

    @Bean(name = "demo")
    public StudentService student(){
        return new StudentService(studentProperties.getId(), studentProperties.getName());
    }
}