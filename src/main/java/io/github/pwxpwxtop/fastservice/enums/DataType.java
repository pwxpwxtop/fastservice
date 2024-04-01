package io.github.pwxpwxtop.fastservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum DataType {
    STRING("varchar","varchar" ),//字符串
    BIT("bit", "bit"),//字符串
    INT("int", "int"),//短整形
    LONG("bigint",  "bigint"),//长整形
    CHAR("char", "char"),//字符类型
    FLOAT("float", "float4"),//精度类型
    DOUBLE("double", "float8"),//双精度类型
    DECIMAL("decimal", "decimal"),//金币类型
    DATETIME("datetime", "timestamp"),//日期时间
    TIME("time", "time"),//时间
    DATE("date", "date"),//日期
    TEXT("text", "text");//文本

    private final String mysql;
    private final String postgres;



}
