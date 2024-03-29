package io.github.pwxpwxtop.fastservice.dao;



import org.apache.ibatis.annotations.SelectProvider;
import java.util.List;


public interface Dao {

    @SelectProvider(value = DaoProvider.class, method = "sql")
    List<Object> sql(String sql);
}