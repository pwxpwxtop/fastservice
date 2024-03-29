package io.github.pwxpwxtop.fastservice.bean;


import io.github.pwxpwxtop.fastservice.dao.Dao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class FastServiceBeans {

    @Bean
    public MapperBean mapperBean(){
        return new MapperBean(Dao.class);
    }


}
