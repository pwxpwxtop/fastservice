package io.github.pwxpwxtop.fastservice.bean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.Resource;

public class MapperBean implements FactoryBean {

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    private Class mapper;
    
    public MapperBean(Class mapper){
        this.mapper = mapper;
    }

    @Override
    public Object getObject() throws Exception {
        sqlSessionFactory.getConfiguration().addMapper(mapper);
        Object obj = sqlSessionFactory.openSession().getMapper(mapper);
        return obj;
    }

    @Override
    public Class<?> getObjectType() {
        return mapper;
    }
}
