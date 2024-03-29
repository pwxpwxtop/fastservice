package io.github.pwxpwxtop.fastservice.model;

import lombok.Data;

/**
 *
 * 配置信息
 *
 */
@Data
public class ImpPro<T> {

    //是否开启bo验证
    Boolean isBo = false;

    //是否开启自动填充数据
    Boolean isAutoFill = false;

    T t;

}
