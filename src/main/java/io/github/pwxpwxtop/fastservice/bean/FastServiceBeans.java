package io.github.pwxpwxtop.fastservice.bean;


import io.github.pwxpwxtop.fastservice.dao.Dao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @description:
 * @author: pwx
 * @data: 2022/11/19 20:45
 * @version: 1.0
 */
@Configuration
public class FastServiceBeans {

    @Bean
    public MapperBean mapperBean(){
        return new MapperBean(Dao.class);
    }



}
