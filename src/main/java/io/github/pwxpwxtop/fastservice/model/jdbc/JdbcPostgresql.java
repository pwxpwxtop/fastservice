package io.github.pwxpwxtop.fastservice.model.jdbc;

import io.github.pwxpwxtop.fastservice.enums.DatabaseType;
import lombok.Data;

/**
 * Description:
 * Author:         PWX
 * CreateDate:     2024/4/2 15:52
 */
@Data
public class JdbcPostgresql extends Jdbc{


    public JdbcPostgresql() {
        setDatabaseType(DatabaseType.POSTGRESQL);
        setDriver("org.postgresql.Driver");
        setUrl("jdbc:postgresql://127.0.0.1:5432/test");
        setUsername("postgres");
        setPassword("123456");
    }


}
