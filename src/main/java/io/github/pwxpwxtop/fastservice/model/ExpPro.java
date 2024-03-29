package io.github.pwxpwxtop.fastservice.model;

import lombok.Data;

import java.util.List;


@Data
public class ExpPro {

    //查询指定主键
    private String idName = "id";


    //是否开启分表
    private Boolean isSubTable = false;

    //导出全部数据
    private Boolean isAll = false;

    //表格数量,isSubTable = true, 开始进行分表操作
    private Long tableSize = 500L;

    //模板名称
    private String fileName;

    //需要导出的id
    private List<String> ids;

    //导出的列
    private List<String> columns;


}
