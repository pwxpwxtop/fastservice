package com.example.conf;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description:
 * @Author: PWX
 * @CreateDate: 2024/2/24 19:37
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/2/24 19:37
 * @Version: 1.0
 */
@Component
public class MyBatisConf implements MetaObjectHandler{

    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        metaObject.setValue("createTime", date);
        metaObject.setValue("updateTime", date);
        setFieldValByName("deleteState", 0, metaObject);
    }

    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", new Date());
    }
}
