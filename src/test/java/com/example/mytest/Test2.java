package com.example.mytest;

import com.example.model.MyUser;
import io.github.pwxpwxtop.fastservice.enums.DatabaseType;
import io.github.pwxpwxtop.fastservice.model.jdbc.JdbcMysql;
import io.github.pwxpwxtop.fastservice.model.jdbc.JdbcPostgresql;
import io.github.pwxpwxtop.fastservice.utils.GenCodeUtils;
import io.github.pwxpwxtop.fastservice.utils.SqlUtils;
import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Author: PWX
 * @CreateDate: 2024/3/27 12:50
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/3/27 12:50
 * @Version: 1.0
 */

public class Test2 {

//   @Test
   public void test01(){
       System.out.println(SqlUtils.getCreateTable(MyUser.class, DatabaseType.MYSQL));
   }


//    @Test
    public void test02(){
        //只会帮我们生成文件，不会帮我们生成sql语句
        GenCodeUtils.genCode(MyUser.class,
                null,
                "D:\\file\\IDEA\\FastService\\FastService\\src\\test\\java\\com\\example",
                "com.example");
    }

//    @Test
    public void test03(){
        JdbcMysql jdbcMysql = new JdbcMysql();
        GenCodeUtils.genCode(MyUser.class,
                jdbcMysql,
                "D:\\file\\IDEA\\FastService\\FastService\\src\\test\\java\\com\\example",
                "com.example");
    }

    @Test
    public void test04(){
        JdbcMysql jdbc = new JdbcMysql();
        GenCodeUtils.genCode(MyUser.class,
                jdbc,
                "D:\\file\\IDEA\\FastService\\FastService\\src\\test\\java\\com\\example",
                "com.example");
    }


}
