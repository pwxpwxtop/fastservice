package io.github.pwxpwxtop.fastservice.animation.sql;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecimalPoint {

    int value() default 2;
}
