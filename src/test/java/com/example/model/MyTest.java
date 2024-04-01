package com.example.model;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.pwxpwxtop.fastservice.animation.Bo;
import io.github.pwxpwxtop.fastservice.animation.Vo;
import io.github.pwxpwxtop.fastservice.animation.sql.*;

import io.github.pwxpwxtop.fastservice.animation.sql.Longs;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description:
 * @Author: PWX
 * @CreateDate: 2024/2/23 11:45
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/2/23 11:45
 * @Version: 1.0
 */
@Data
@TableName("my_test")
public class MyTest extends BaseModel{

    @TableId(type = IdType.ASSIGN_ID)
    @ExcelProperty("唯一id")
    @Index(100) @Longs
    private java.lang.Long id;

    @ExcelProperty(value = "姓名")
    @Index(9) @Varchar @Default("小白")
    private String name;

    @Bo(regex = "([1-9][0-9]{0,1}|100|0)", msg = "不在年龄范围内")
    @ExcelProperty(value = "年龄")
    @Index(4) @NotNull @Default("0")
    private Integer age;

    @Bit
    private byte sexInt;

    @Char
    private char fastNam;

    @Index(5)
    @ExcelProperty("性别")
    @Default("女")
    private String sex;


    @ExcelProperty(value = "手机号码")
    @Index(8)
    private java.lang.Long phone;



    @ExcelProperty(value = "金币")
    @Decimal(length = 12, point = 3)
    private BigDecimal total;


    @Dates
    @ExcelProperty(value = "日期")
    private String date;


    @Datetimes
    private String datetime;


    @Dates
    private Date date2;

    @Times
    private String time;

    @Text
    private String text;

    @Float32
    private float scor1;

    @Double64
    private float scor2;

    @Vo(exist = false)
    @TableLogic(value = "0" , delval = "1")
    @TableField(fill = FieldFill.INSERT)
    @ExcelIgnore
    @Index(0)
    private Integer deleteState;


    //创建时间
    @Bo(exist = false)
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Index(0)
    private Date createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Index(0)
    private Date updateTime;

    //保存更新人的id或者更新人的姓名
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Index(0)
    private String updateBy;
}
