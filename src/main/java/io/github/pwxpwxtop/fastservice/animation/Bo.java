package io.github.pwxpwxtop.fastservice.animation;

import io.github.pwxpwxtop.fastservice.enums.BoType;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Bo {

    //表示该字段关联数据库字段
    boolean exist() default true;

    BoType [] type() default {};

    String regex() default "";//正则匹配

    String msg() default "";//消息
}
