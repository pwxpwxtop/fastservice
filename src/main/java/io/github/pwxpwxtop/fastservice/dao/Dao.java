package io.github.pwxpwxtop.fastservice.dao;



import org.apache.ibatis.annotations.SelectProvider;
import java.util.List;
import java.util.Map;


public interface Dao {

    @SelectProvider(value = DaoProvider.class, method = "sql")
    List<Map<String, Object>> sql(String sql);
}