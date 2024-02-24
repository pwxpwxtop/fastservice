package io.github.pwxpwxtop.fastservice.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.pwxpwxtop.fastservice.animation.Vo;
import io.github.pwxpwxtop.fastservice.enums.VoType;
import io.github.pwxpwxtop.fastservice.exception.VoException;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: PWX
 * @CreateDate: 2024/2/23 18:15
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/2/23 18:15
 * @Version: 1.0
 */
public class VoUtils {


    public static <T> QueryWrapper<T> getQueryWrapper(Object obj) throws VoException {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        ArrayList<String> arrayList = new ArrayList<>();
        getQueryWrapper(obj, obj.getClass(),  queryWrapper, arrayList);
        queryWrapper.select(arrayList.toArray(new String[arrayList.size()]));
        return queryWrapper;
    }



    public static <T> QueryWrapper<T> getQueryWrapper(Object obj, Class<?> objClass,  QueryWrapper<T> queryWrapper,ArrayList<String> columns) throws VoException {
        Class<?> superClass = objClass.getSuperclass();
        if (superClass != null){
            getQueryWrapper(obj, superClass, queryWrapper, columns);
        }
        Field[] fields =  objClass.getDeclaredFields();//获取实体类所有字段
        for (Field field : fields) {//添加所有字段eq查询
            field.setAccessible(true);//开启范围属性权限
            String name = field.getName();//获取字段名称
            Vo vo = null;
            if (field.isAnnotationPresent(Vo.class)) {//判断是否保护Vo注解
                vo = field.getDeclaredAnnotation(Vo.class);
            }
            TableField tableField = null;
            if (field.isAnnotationPresent(TableField.class)) {//判断是否保护Vo注解
                tableField = field.getDeclaredAnnotation(TableField.class);
            }

            boolean isVo = false;
            if (vo == null) {
                isVo = true;
            } else {
                isVo = vo.exist();
            }

            if (isVo) {
                String sqlName = Tran.toUnderLine(name);//名称转换为数据库名
                if (tableField != null){
                    if (tableField.exist()){
                        columns.add(sqlName);
                    }
                }else {
                    columns.add(sqlName);
                }
                try {

                    Object value = field.get(obj);//获取value值

                    if (value != null) {
                        if (vo != null) {
                            regexValue(value, vo);//匹配Value
                            voType(vo, queryWrapper, sqlName, value);
                        } else {
                            queryWrapper.eq(sqlName, value);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }

        return queryWrapper;
    }

    private static <T> void voType(Vo vo, QueryWrapper<T> queryWrapper , String sqlName, Object value){
        VoType [] voType = vo.type();
        for (VoType type : voType) {
            switch (type){
                case EQ: queryWrapper.eq(sqlName, value);break;
                case NE: queryWrapper.ne(sqlName, value);break;
                case LIKE: queryWrapper.like(sqlName, value);
                case LIKE_LEFT: queryWrapper.likeLeft(sqlName, value);
                case LIKE_RIGHT: queryWrapper.likeRight(sqlName, value);break;
                case LE: queryWrapper.le(sqlName, value);break;
                case LT: queryWrapper.lt(sqlName, value);break;
                case GE: queryWrapper.ge(sqlName, value);break;
                case GT: queryWrapper.gt(sqlName, value);break;
                case NOT_NULL: queryWrapper.isNotNull(sqlName);break;
                case NULL: queryWrapper.isNull(sqlName);break;
            }
        }
    }

    /**
     *
     * @param value 匹配内容
     * @param vo vo
     * @throws VoException
     */
    private static void regexValue(Object value, Vo vo) throws VoException {
        if (vo.exist()){//对字段value进行处理
            String regex = vo.regex();//正则匹配
            if (!"".equals(regex)){
                boolean isMatch = Pattern.matches(regex, String.valueOf(value));
                if (!isMatch){
                    throw new VoException(vo.msg());
                }
            }
        }

    }




}
