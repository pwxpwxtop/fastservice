package io.github.pwxpwxtop.fastservice.animation;

import io.github.pwxpwxtop.fastservice.enums.VoType;

import java.lang.annotation.*;

/**
 * @Description: 视图层
 * @Author: PWX
 * @CreateDate: 2024/2/23 13:09
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/2/23 13:09
 * @Version: 1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Vo {

    boolean exist() default true;

    VoType [] type() default {VoType.EQ};

    String regex() default "";//正则匹配

    String msg() default "";//消息
}
