package org.example.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 作业：Week05 周四 必做 2
 */
@Configuration
public class SpringDemoMain {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Man man = (Man) context.getBean("man");
        man.active();
    }

    @Bean
    public Apple apple() {
        return new Apple();
    }
}
