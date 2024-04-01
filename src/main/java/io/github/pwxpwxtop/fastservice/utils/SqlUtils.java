package io.github.pwxpwxtop.fastservice.utils;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.github.pwxpwxtop.fastservice.animation.sql.*;
import io.github.pwxpwxtop.fastservice.animation.sql.Dates;
import io.github.pwxpwxtop.fastservice.animation.sql.Double64;
import io.github.pwxpwxtop.fastservice.animation.sql.Float32;
import io.github.pwxpwxtop.fastservice.animation.sql.Longs;
import io.github.pwxpwxtop.fastservice.enums.DataType;
import io.github.pwxpwxtop.fastservice.enums.DatabaseType;
import io.github.pwxpwxtop.fastservice.model.SqlProperties;

import java.lang.reflect.Field;

import java.util.*;

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

    public static String getCreateTable(Class cls, DatabaseType databaseType){
        switch (databaseType){
            case MYSQL:
                return getCreateTableMySql(cls);
            case POSTGRESQL:
                return getCreateTablePostgreSql(cls);
        }
        return null;
    }

    //获取一个创建表格的PostgreSql语句
    public static String getCreateTablePostgreSql(Class cls){
        List<SqlProperties> fieldList = new ArrayList<>();
        createTable(cls, fieldList);

        //去重复
        HashSet<String> set = new HashSet<>(fieldList.size());
        List<SqlProperties> result = new ArrayList<>(fieldList.size());
        for (SqlProperties sqlProperties : fieldList) {
            String name = sqlProperties.getColumnName();
            if (set.add(name)){
                result.add(sqlProperties);
            }
        }
        result.sort((o1, o2) -> o2.getIndex() - o1.getIndex());
        String tableName = getTableName(cls);
        StringBuilder builder = new StringBuilder();


        builder.append(" CREATE TABLE IF NOT EXISTS ").append(tableName);
        StringJoiner joiner = new StringJoiner("", "( ", " )");
        StringJoiner fieldJoin = new StringJoiner(" , ");

        List<String> commentList = new ArrayList<>();
        for (SqlProperties entity : result) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(entity.getColumnName()).append(" ");

            stringBuilder.append(entity.getDataType().getPostgres());

            if (entity.getDataType() == DataType.STRING){
                stringBuilder.append("(").append(entity.getLength()).append(")");
            }else if (entity.getDataType() == DataType.DECIMAL){
                String length = "10";
                if (entity.getLength() < 65) {
                    length = entity.getLength() + "";
                }
                stringBuilder.append("(").append(length).append(",").append(entity.getDecimalPoint()).append(")");
            }

            if (entity.isNotNull()){
                stringBuilder.append(" not null ");
            }

            if (entity.getDef() != null && !entity.getDef().isEmpty()){
                stringBuilder.append(" default '").append(entity.getDef()).append("'");
            }

            if (entity.getComment() != null && !entity.getComment().isEmpty()){
                commentList.add("comment on column " + tableName + "." + entity.getColumnName() + " is '" + entity.getComment() + "';");

            }
            fieldJoin.add(stringBuilder.toString());
        }
        String keyId = getPrimaryKey(cls);
        if (keyId != null){
            fieldJoin.add("primary key (" + keyId + ")");
        }
        joiner.add(fieldJoin.toString());

        String commentListStr = "";
        for (String s : commentList) {
            commentListStr += s;
        }
        return builder.append(joiner)+ ";" + commentListStr;
    }



    //获取一个创建表格的Mysql语句
    public static String getCreateTableMySql(Class cls){
        List<SqlProperties> fieldList = new ArrayList<>();
        createTable(cls, fieldList);

        //去重复
        HashSet<String> set = new HashSet<>(fieldList.size());
        List<SqlProperties> result = new ArrayList<>(fieldList.size());
        for (SqlProperties sqlProperties : fieldList) {
            String name = sqlProperties.getColumnName();
            if (set.add(name)){
                result.add(sqlProperties);
            }
        }
        result.sort((o1, o2) -> o2.getIndex() - o1.getIndex());
        String tableName = getTableName(cls);
        StringBuilder builder = new StringBuilder();
        builder.append(" CREATE TABLE IF NOT EXISTS  `").append(tableName).append("`");
        StringJoiner joiner = new StringJoiner("", "( ", " )");
        StringJoiner fieldJoin = new StringJoiner(" , ");
        for (SqlProperties entity : result) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" `").append(entity.getColumnName()).append("` ");
            stringBuilder.append(entity.getDataType().getMysql());

            if (entity.getDataType() == DataType.STRING){
                stringBuilder.append("(").append(entity.getLength()).append(")");
            }else if (entity.getDataType() == DataType.DECIMAL){
                String length = "10";
                if (entity.getLength() < 65) {
                    length = entity.getLength() + "";
                }
                stringBuilder.append("(").append(length).append(",").append(entity.getDecimalPoint()).append(")");
            }

            if (entity.isNotNull()){
                stringBuilder.append(" not null ");
            }

            if (entity.getDef() != null && !entity.getDef().isEmpty()){
                stringBuilder.append(" default '").append(entity.getDef()).append("'");
            }

            if (entity.getComment() != null && !entity.getComment().isEmpty()){
                stringBuilder.append(" comment '").append(entity.getComment()).append("'");
            }
            fieldJoin.add(stringBuilder.toString());
        }
        String keyId = getPrimaryKey(cls);
        if (keyId != null){
            fieldJoin.add("primary key (" + keyId + ")");
        }
        joiner.add(fieldJoin.toString());
        return builder.append(joiner).toString() + ";";
    }

    //获取表格名称
    private  static String getTableName(Class<?> cls){
        if (cls.isAnnotationPresent(TableName.class)){
            TableName tableName = cls.getDeclaredAnnotation(TableName.class);
            if ("".equals(tableName.value().trim())){
                return Tran.toUnderLine(cls.getSimpleName());
            }else{
                return tableName.value();
            }
        }else {
            return Tran.toUnderLine(cls.getSimpleName());
        }
    }

    public  static String getPrimaryKey( Class<?> cls){
        Field[] fields =  cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(TableId.class)){
                TableId tableId = field.getDeclaredAnnotation(TableId.class);
                String keyId = tableId.value();
                if ("".equals(keyId.trim())){
                    return field.getName();
                }else{
                    return tableId.value();
                }

            }

        }
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null){
            return getPrimaryKey(superClass);
        }
        return null;
    }


    public  static void createTable(Class<?> cls, List<SqlProperties> fieldList){

        Field[] fields =  cls.getDeclaredFields();
        for (Field field : fields) {
            SqlProperties properties = new SqlProperties();
            field.setAccessible(true);
            String name = field.getName();
            if (field.isAnnotationPresent(Index.class)){
                Index index= field.getAnnotation(Index.class);
                properties.setIndex(index.value());
            }else{
                properties.setIndex(1);
            }

            if (field.isAnnotationPresent(NotNull.class)){
                properties.setNotNull(true);
            }


            properties.setDataType(getSqlDataType(field.getType()));
            //设置数据类型----------------------------------------------------
            if (field.isAnnotationPresent(Varchar.class)){
                Varchar varchar = field.getAnnotation(Varchar.class);
                properties.setDataType(DataType.STRING);
                properties.setLength(varchar.value());
            }

            if (field.isAnnotationPresent(Char.class)){
                properties.setDataType(DataType.CHAR);
            }

            if (field.isAnnotationPresent(Bit.class)){
                properties.setDataType(DataType.BIT);
            }

            if (field.isAnnotationPresent(Int.class)){
                properties.setDataType(DataType.INT);
            }

            if (field.isAnnotationPresent(Longs.class)){
                properties.setDataType(DataType.LONG);
            }

            if (field.isAnnotationPresent(Float32.class)){
                properties.setDataType(DataType.FLOAT);
            }

            if (field.isAnnotationPresent(Double64.class)){
                properties.setDataType(DataType.DOUBLE);
            }

            if (field.isAnnotationPresent(Datetimes.class)){
                properties.setDataType(DataType.DATETIME);
            }

            if (field.isAnnotationPresent(Times.class)){
                properties.setDataType(DataType.DATE);
            }

            if (field.isAnnotationPresent(Dates.class)){
                properties.setDataType(DataType.DATE);
            }

            if (field.isAnnotationPresent(Decimal.class)){
                Decimal decimal = field.getAnnotation(Decimal.class);
                properties.setDataType(DataType.DECIMAL);
                properties.setLength(decimal.length());
                properties.setDecimalPoint(decimal.point());
            }

            if (field.isAnnotationPresent(Text.class)){
                properties.setDataType(DataType.TEXT);
            }

            //----------------------------------------------------


            if (field.isAnnotationPresent(Length.class)){
                Length length = field.getAnnotation(Length.class);
                properties.setLength(length.value());
            }


            if (field.isAnnotationPresent(Default.class)){
                Default aDefault = field.getAnnotation(Default.class);
                properties.setDef(aDefault.value());
            }


            if (field.isAnnotationPresent(DecimalPoint.class)){
                DecimalPoint decimalPoint = field.getAnnotation(DecimalPoint.class);
                properties.setDecimalPoint(decimalPoint.value());
            }

            if (field.isAnnotationPresent(Comment.class)){
                Comment comment = field.getAnnotation(Comment.class);
                properties.setComment(comment.value());
            }

            if (field.isAnnotationPresent(ExcelProperty.class)){
                ExcelProperty property = field.getAnnotation(ExcelProperty.class);
                properties.setComment(property.value()[0]);
            }

            if (field.isAnnotationPresent(TableField.class)){
                TableField tableField = field.getAnnotation(TableField.class);
                String fieldName = tableField.value();
                if (tableField.exist()){//true:表示参与
                    if ("".equals(fieldName.trim())){
                        properties.setColumnName(Tran.toUnderLine(name));

                    }else{
                        properties.setColumnName(fieldName);
                    }
                }else{
                    properties = null;
                }
            }else {
                properties.setColumnName(Tran.toUnderLine(name));

            }

            if (properties != null){
                fieldList.add(properties);
            }
        }
        Class<?> superClass = cls.getSuperclass();
        if (superClass != null){
            createTable( superClass, fieldList);
        }
    }

    public static DataType getSqlDataType(Class<?> cls){
        String typeName = cls.getTypeName();
        switch (typeName){
            case "java.lang.Integer":
                return DataType.INT;
            case "java.lang.Long":
                return DataType.LONG;
            case "java.lang.Character":
                return DataType.CHAR;
            case "java.lang.Float":
                return DataType.FLOAT;
            case "java.lang.Double":
                return DataType.DOUBLE;
            case "java.math.BigDecimal":
                return DataType.DECIMAL;
            case "java.math.Date":
                return DataType.DATETIME;
            case "java.lang.String":
            default:
                return DataType.STRING;
        }
    }


}
