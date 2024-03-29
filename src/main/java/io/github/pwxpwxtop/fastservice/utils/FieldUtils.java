package io.github.pwxpwxtop.fastservice.utils;


import com.baomidou.mybatisplus.annotation.TableId;

import java.lang.reflect.Field;

public class FieldUtils {



    public static String getId(Class<?> cls) {
        Class<?> superClass = cls.getSuperclass();//获取父级类对象
        String idName = "id";
        if (superClass != null){
            idName = getId(superClass);
        }
        Field[] fields =  cls.getDeclaredFields();//获取实体类所有字段
        for (Field field : fields) {//添加所有字段eq查询
            field.setAccessible(true);
            String id =  field.getName();

            if (field.isAnnotationPresent(TableId.class)) {
                TableId tableId = field.getAnnotation(TableId.class);
                String  value = tableId.value();
                if (value.length() == 0){
                    return "id";
                }else {
                    return value;
                }
            }else{
                return Tran.toUnderLine(id);
            }
        }
        return idName;
    }


}
