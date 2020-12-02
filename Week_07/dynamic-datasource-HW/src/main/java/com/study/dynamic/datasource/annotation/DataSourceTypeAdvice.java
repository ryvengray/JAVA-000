package com.study.dynamic.datasource.annotation;

import com.study.dynamic.datasource.constant.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceTypeAdvice {

    DataSourceType sourceType() default DataSourceType.MASTER;
}
