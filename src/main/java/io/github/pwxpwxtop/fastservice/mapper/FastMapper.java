package io.github.pwxpwxtop.fastservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface FastMapper<T> extends BaseMapper<T> {

    @Select("${sql}")
    List<T> executeSQL(@Param("sql") String sql);

}
