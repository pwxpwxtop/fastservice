package io.github.pwxpwxtop.fastservice.animation.sql;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Varchar {

    //默认长度
    long value() default 255;

}
