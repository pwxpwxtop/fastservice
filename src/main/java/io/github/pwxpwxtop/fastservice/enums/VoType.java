package io.github.pwxpwxtop.fastservice.enums;

public enum VoType {

    EQ, //相等查询
    NE, //不等于
    LIKE,//全模糊查询
    LIKE_LEFT,//左模糊查询
    LIKE_RIGHT,//右模糊查询
    NOT_LIKE,//反向模糊匹配
    IN,//包含
    NOT_IN,//不包含
    EXISTS,//存在
    NOT_EXISTS,//不存在
    LT, //小于查询
    LE, //小于等于查询
    GT, //大于查询
    GE,  //大于等于查询
    NOT_NULL,//不能为空
    NULL,//为空
}
