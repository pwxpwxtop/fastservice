package io.github.pwxpwxtop.fastservice.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pwxpwxtop.fastservice.r.R;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @description:
 * @author: pwx
 * @data: 2022/9/6 22:41
 * @version: 1.0
 */
public interface Service<T> {

    R data(T t, Page<T> page);

    R insert(T t);

    R update(T t);

    R delete(@RequestBody T t);
}
