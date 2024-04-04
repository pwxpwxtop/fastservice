package io.github.pwxpwxtop.fastservice.model;

import io.github.pwxpwxtop.fastservice.enums.DataType;
import lombok.Data;


@Data
public class SqlProperties {


    private int index = 0;

    //
    private String columnName;

    //数据类型
    private DataType dataType;

    //是否为空
    private boolean isNotNull;

    //数据长度
    private long length = 255L;

    //小数点
    private int decimalPoint = 0;

    private String def = null;

    //备注
    private String comment;

}
