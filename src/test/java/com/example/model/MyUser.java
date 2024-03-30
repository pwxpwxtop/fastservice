package com.example.model;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import io.github.pwxpwxtop.fastservice.animation.Bo;
import io.github.pwxpwxtop.fastservice.animation.Vo;
import io.github.pwxpwxtop.fastservice.enums.BoType;
import io.github.pwxpwxtop.fastservice.enums.VoType;
import lombok.Data;


import java.util.Map;

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
public class MyUser extends BaseModel{

    @Bo(type = { BoType.NOT_NULL_STR, BoType.FILTER, BoType.FILTER})
    @Vo(type = {VoType.LIKE})
    @ExcelProperty(value = "姓名")
    private String name;

    @Bo(regex = "([1-9][0-9]{0,1}|100|0)", msg = "不在年龄范围内")
    @ExcelProperty(value = "年龄")
    private Integer age;

    @ExcelProperty("性别")
    private String sex;


    @ExcelProperty(value = "手机号码")
    private Long phone;

    @Vo(exist = false)
    @TableLogic(value = "0" , delval = "1")
    @TableField(fill = FieldFill.INSERT)
    @ExcelIgnore
    private Integer deleteState;

    @Bo(type = { BoType.NOT_NULL_STR}, exist = false)
    @TableField(exist = false)
    @ExcelIgnore//忽略这个字段
    private Map<String, Object> map;
}
