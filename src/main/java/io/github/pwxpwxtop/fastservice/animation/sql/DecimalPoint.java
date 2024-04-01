package io.github.pwxpwxtop.fastservice.animation.sql;


import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DecimalPoint {

    /**
     * 小数点后的位数
     * @return
     */
    int value() default 2;
}
