package io.github.pwxpwxtop.fastservice.model.jdbc;

import io.github.pwxpwxtop.fastservice.enums.DatabaseType;
import lombok.Data;

/**
 * Description:
 * Author:         PWX
 * CreateDate:     2024/4/2 15:52
 */
@Data
public class Jdbc {

    //数据库类型
    private DatabaseType databaseType;

    //驱动
    private String driver;

    private String url;

    //用户名
    private String username;

    //密码
    private String password;


}
