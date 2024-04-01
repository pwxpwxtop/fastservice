package com.example.mytest;

import com.example.model.MyTest;
import com.example.model.MyUser;
import io.github.pwxpwxtop.fastservice.enums.DatabaseType;
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

public class TestExcel2 {

   @Test
   public void test01(){
       System.out.println(SqlUtils.getCreateTable(MyTest.class, DatabaseType.MYSQL));
   }

}
