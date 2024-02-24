package com.example.conf;


import io.github.pwxpwxtop.fastservice.r.R;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @description: 全局异常拦截处理
 * @author: pwx
 * @data: 2022/9/24 22:58
 * @version: 1.0
 */

public class GlobalExceptionHandler {
    // 全局异常拦截
    @ExceptionHandler
    public R Exception(Exception e) {
        e.printStackTrace();
        return R.no(e.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public R NullPointerException(NullPointerException e) {
        e.printStackTrace();
        return R.no(e, "空指针异常");
    }
}
