package io.github.pwxpwxtop.fastservice.model.jdbc;

import io.github.pwxpwxtop.fastservice.enums.DatabaseType;
import lombok.Data;

/**
 * Description:
 * Author:         PWX
 * CreateDate:     2024/4/2 15:52
 */
@Data
public class JdbcMysql extends Jdbc{


    public JdbcMysql() {
        setDatabaseType(DatabaseType.MYSQL);
        setDriver("com.mysql.cj.jdbc.Driver");
        setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8");
        setUsername("root");
        setPassword("123456");
    }


}
