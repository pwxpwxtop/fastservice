package com.example.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.pwxpwxtop.fastservice.animation.Bo;
import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: PWX
 * @CreateDate: 2024/2/24 16:36
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/2/24 16:36
 * @Version: 1.0
 */
@Data
public class BaseModel {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    //创建时间
    @Bo(exist = false)
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    //保存更新人的id或者更新人的姓名
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
