package com.example.model;


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

    @Bo(type = { BoType.NOT_STR, BoType.FILTER})
    private String name;

    @Bo(regex = "([1-9][0-9]{0,1}|100|0)", msg = "不在年龄范围内")
    @Vo(type = {VoType.GT, VoType.NOT_NULL}, regex = "([1-9][0-9]{0,1}|100|0)", msg = "不在年龄范围内")
    private Integer age;

    @Bo(type = { BoType.REPEAT}, regex = "^1[3-9]\\d{9}$", msg = "手机号码不正确")
    private Long phone;

    @Vo(exist = false)
    @TableLogic(value = "0" , delval = "1")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteState;

    @Bo(type = { BoType.NOT_STR}, exist = false)
    @TableField(exist = false)
    private Map<String, Object> map;
}
