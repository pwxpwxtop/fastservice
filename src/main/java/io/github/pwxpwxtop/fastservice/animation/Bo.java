package io.github.pwxpwxtop.fastservice.animation;

import io.github.pwxpwxtop.fastservice.enums.BoType;

import java.lang.annotation.*;

/**
 * @Description: 数据库层
 * @Author: PWX
 * @CreateDate: 2024/2/23 13:09
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/2/23 13:09
 * @Version: 1.0
 */
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
