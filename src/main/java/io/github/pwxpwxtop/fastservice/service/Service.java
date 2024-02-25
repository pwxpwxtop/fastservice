package io.github.pwxpwxtop.fastservice.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pwxpwxtop.fastservice.r.R;
import org.springframework.web.bind.annotation.RequestBody;


public interface Service<T> {

    R data(T t, Page<T> page);

    R insert(T t);

    R update(T t);

    R delete(@RequestBody T t);
}
