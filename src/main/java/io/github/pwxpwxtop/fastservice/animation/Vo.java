package io.github.pwxpwxtop.fastservice.animation;

import io.github.pwxpwxtop.fastservice.enums.VoType;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Vo {

    boolean exist() default true;

    VoType [] type() default {VoType.EQ};

    String regex() default "";//正则匹配

    String msg() default "";//消息
}
