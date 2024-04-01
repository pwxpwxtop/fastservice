package io.github.pwxpwxtop.fastservice.animation.sql;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Decimal {
    int length() default 10;
    int point() default 2;
}
