package io.github.pwxpwxtop.fastservice.model;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @description: 雪花算法的id实体
 * @author: pwx
 * @data: 2022/9/14 19:12
 * @version: 1.0
 */
@Data
public class ModelSnowflake {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //保存更新人的id或者更新人的姓名
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    //删除状态
    @TableLogic(value = "0" , delval = "1")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteState;


}
