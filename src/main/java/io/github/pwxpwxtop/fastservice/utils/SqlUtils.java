package io.github.pwxpwxtop.fastservice.utils;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.pwxpwxtop.fastservice.exception.BoException;

import java.lang.reflect.Field;
import java.util.StringJoiner;
import java.util.UUID;

public class SqlUtils {


    public static <T> StringJoiner getInsertSql(T t){
        StringJoiner joinerVal = new StringJoiner(" , ", "( ", " )");
        sqlVal(t, t.getClass(), joinerVal);
        return joinerVal;
    }


    public  static <T> void sqlVal(T t, Class<?> cls, StringJoiner stringJoiner){
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null){
            sqlVal(t, superClass, stringJoiner);
        }
        Field[] fields =  cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = null;
            if (field.isAnnotationPresent(ExcelIgnore.class)){
                continue;
            }

            try {
                value = field.get(t);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (field.isAnnotationPresent(TableField.class)){
                TableField tableField = field.getAnnotation(TableField.class);
                if (tableField.exist()){//true:表示参与
                    if (value == null){
                        setId(stringJoiner, field);
                    }else{
                        stringJoiner.add("'"+value+"'");
                    }
                }
            }else {
                if (value == null){
                    setId(stringJoiner, field);
                }else{
                    stringJoiner.add("'"+value+"'");
                }
            }
        }
    }


    public static void setId(StringJoiner stringJoiner, Field field){
        if (field.isAnnotationPresent(TableId.class)) {
            TableId tableId = field.getDeclaredAnnotation(TableId.class);
            switch (tableId.type()){
                case ASSIGN_ID:
                    stringJoiner.add(IdWorker.getIdStr());
                    break;//雪花id
                case ASSIGN_UUID:
                    stringJoiner.add("'"+ UUID.randomUUID().toString().replace("-","")+"'");
                    break;//uuid
                default:
                    stringJoiner.add(null);
            }
        }else{
            stringJoiner.add(null);
        }
    }

    public  static  <T> void sqlKey(T t, Class<?> cls, StringJoiner stringJoiner){
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null){
            sqlKey(t, superClass, stringJoiner);
        }
        Field[] fields =  cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ExcelIgnore.class)){
                continue;
            }

            if (field.isAnnotationPresent(TableField.class)){
                TableField tableField = field.getAnnotation(TableField.class);
                String name = tableField.value();
                if (tableField.exist()){//true:表示参与
                    if (name.trim().length() == 0){
                        stringJoiner.add(Tran.toUnderLine(field.getName()));
                    }else{
                        stringJoiner.add(name);
                    }
                }
            }else {
                stringJoiner.add(Tran.toUnderLine(field.getName()));
            }
        }
    }



}
