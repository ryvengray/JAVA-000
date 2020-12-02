package com.study.dynamic.datasource.aop;

import com.study.dynamic.datasource.annotation.DataSourceTypeAdvice;
import com.study.dynamic.datasource.config.DynamicRoutingDataSource;
import com.study.dynamic.datasource.constant.DataSourceType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(value = 1)
@Component
public class DynamicDataSourceAspect {


    /**
     * 所有添加了DataSurceAnnotation的方法都进入切面
     */
    @Pointcut("@annotation(com.study.dynamic.datasource.annotation.DataSourceTypeAdvice)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        //在执行方法之前设置使用哪个数据源
        DataSourceTypeAdvice ds = method.getAnnotation(DataSourceTypeAdvice.class);
        if (ds == null) {
            DynamicRoutingDataSource.setDataSource(DataSourceType.MASTER);
        } else if (ds.sourceType() == DataSourceType.MASTER){
            DynamicRoutingDataSource.setDataSource(DataSourceType.MASTER);
        }else {
            DynamicRoutingDataSource.setDataSource(DataSourceType.getRandomSlaveType());
        }
        try {
            return point.proceed();
        } finally {
            DynamicRoutingDataSource.clearDataSource();
        }
    }
}
