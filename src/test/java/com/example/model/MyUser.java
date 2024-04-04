package com.example.model;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.pwxpwxtop.fastservice.animation.Bo;
import io.github.pwxpwxtop.fastservice.animation.sql.Index;
import io.github.pwxpwxtop.fastservice.animation.sql.Int8;
import io.github.pwxpwxtop.fastservice.animation.sql.NotNull;
import io.github.pwxpwxtop.fastservice.animation.Vo;
import io.github.pwxpwxtop.fastservice.animation.sql.Varchar;
import io.github.pwxpwxtop.fastservice.enums.BoType;
import io.github.pwxpwxtop.fastservice.enums.VoType;
import lombok.Data;


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
@TableName("my_user")
public class MyUser {

    @TableId(type = IdType.ASSIGN_ID)
    @ExcelProperty("唯一id")
    @Index(10)
    @Int8 @NotNull
    private Long id;


    @Bo(type = { BoType.NOT_NULL_STR, BoType.FILTER, BoType.FILTER})
    @Vo(type = {VoType.LIKE})
    @ExcelProperty(value = "姓名")
    @Index(9) @Varchar(10)
    private String name;

    @Bo(regex = "([1-9][0-9]{0,1}|100|0)", msg = "不在年龄范围内")
    @ExcelProperty(value = "年龄")
    @Index(4) @NotNull
    private Integer age;

    @ExcelProperty("性别")
    @Index(5)
    private String sex;


    @ExcelProperty(value = "手机号码")
    @Index(8)
    private Long phone;

    @Vo(exist = false)
    @TableLogic(value = "0" , delval = "1")
    @TableField(fill = FieldFill.INSERT)
    @ExcelIgnore
    private Integer deleteState;


    //创建时间
    @Bo(exist = false)
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    //保存更新人的id或者更新人的姓名
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
