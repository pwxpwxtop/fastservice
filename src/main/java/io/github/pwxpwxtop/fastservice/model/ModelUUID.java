package io.github.pwxpwxtop.fastservice.model;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;


@Data
public class ModelUUID {

    @ExcelProperty("id")
    @TableId(type = IdType.NONE)
    private String id;

    //创建时间
    @ExcelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //更新时间
    @ExcelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //保存更新人的id或者更新人的姓名
    @ExcelProperty("更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    //删除状态
    @ExcelProperty("删除状态")
    @TableLogic(value = "0" , delval = "1")
    @TableField(fill = FieldFill.INSERT)
    private Integer deleteState;

}
