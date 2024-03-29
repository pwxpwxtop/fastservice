package com.example.mytest;

import com.example.model.MyUser;
import io.github.pwxpwxtop.fastservice.dao.Dao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @Author: PWX
 * @CreateDate: 2024/3/27 12:50
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/3/27 12:50
 * @Version: 1.0
 */
@SpringBootTest
public class TestExcel {

    @Autowired
    Dao dao;

    @Test
    public void test(){

        System.out.println(dao.sql("select * from my_user"));
    }
}
