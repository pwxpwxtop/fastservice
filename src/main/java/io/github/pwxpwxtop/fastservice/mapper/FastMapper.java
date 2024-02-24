package io.github.pwxpwxtop.fastservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description:
 * @author: pwx
 * @data: 2022/9/18 19:49
 * @version: 1.0
 */

public interface FastMapper<T> extends BaseMapper<T> {

    @Select("${sql}")
    List<T> executeSQL(@Param("sql") String sql);

}
