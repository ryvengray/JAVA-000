package com.study.dynamic.datasource.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;


public class GenerateSnowFlakeId implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        SnowFlakeConfig bean = ApplicationContextProvider.getBean(SnowFlakeConfig.class);
        SnowFlakeId snowFlakeId = new SnowFlakeId(bean.getWorkerId(), bean.getDataCenterId());
        return snowFlakeId.nextId();
    }
}
