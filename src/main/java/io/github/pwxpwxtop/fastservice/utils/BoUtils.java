package io.github.pwxpwxtop.fastservice.utils;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.pwxpwxtop.fastservice.animation.Bo;
import io.github.pwxpwxtop.fastservice.enums.BoType;
import io.github.pwxpwxtop.fastservice.exception.BoException;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.pwxpwxtop.fastservice.exception.VoException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Description: 处理实体类
 * @Author: PWX
 * @CreateDate: 2024/2/24 13:34
 * @UpdateUser: IntelliJ IDEA
 * @UpdateDate: 2024/2/24 13:34
 * @Version: 1.0
 */
public class BoUtils {

    public  static  <T, M extends BaseMapper<T>> T getModel(T t, M mapper) throws BoException{
        return getModel(t, t.getClass(), mapper);
    }


    public  static  <T, M extends BaseMapper<T>> T getModel(T t, Class<?> cls,M mapper) throws BoException {
        Class<?> superClass = cls.getSuperclass();//获取父级类对象
        if (superClass != null){
            getModel(t, superClass,  mapper);
        }
        Field[] fields =  cls.getDeclaredFields();//获取实体类所有字段
        for (Field field : fields) {//添加所有字段eq查询
            field.setAccessible(true);
            String name =  field.getName();
            Object value = null;
            try {
                value = field.get(t);//获取value值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Bo bo = null;
            if (field.isAnnotationPresent(Bo.class)) {
                bo = field.getDeclaredAnnotation(Bo.class);
            }

            boolean isVo = false;
            if (bo == null) {
                isVo = true;
            } else {
                isVo = bo.exist();
            }

            if (isVo){//处理字段
                if (bo != null){
                    //正则匹配
                    regexValue(value, bo);
                    voHandle(t, mapper, field, bo, value, name);
                }
            }else{
                try {
                    field.set(t, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return t;
    }



    /**
     *
     * @param t 处理对象
     * @param mapper 数据库mapper
     * @param field 字段
     * @param bo bo注解
     * @param value 字段value值
     * @param name 字段名
     * @param <T>
     * @throws BoException
     */
    public static <T, M extends BaseMapper<T>> void voHandle(T t , M mapper, Field field,Bo bo, Object value, String name) throws BoException {
        BoType [] boTypes = bo.type();
        for (BoType boType : boTypes) {
            switch (boType){
                case NOT_STR:
                    if (value == null || "".equals(String.valueOf(value).trim())){
                        throw new BoException("存在NULL或空字符数据的字段--> " + name);
                    }
                    break;
                case REPEAT:
                    QueryWrapper<T> wrapper = new QueryWrapper<>();
                    wrapper.eq(Tran.toUnderLine(name), value);
                    if (mapper.exists(wrapper)) {
                        throw new BoException("字段 [ " + name + " ] 存在重复的数据--> " + value);
                    }
                    break;
                case FILTER://特殊字符串处理
                    try {
                        if (value instanceof String){
                            field.set(t, FilterStr.stringFilter(String.valueOf(value)));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }


    public  static  <T, M extends BaseMapper<T>> T getModelUpdate(T t, M mapper) throws BoException{
        Map<String, String> map = new HashMap<>();

        return getModelUpdate(t, t.getClass(), mapper,map);
    }


    public  static  <T, M extends BaseMapper<T>> T getModelUpdate(T t, Class<?> cls,M mapper, Map<String, String> map) throws BoException {
        Class<?> superClass = cls.getSuperclass();//获取父级类对象
        if (superClass != null){
            getModelUpdate(t, superClass,  mapper, map);
        }
        Field[] fields =  cls.getDeclaredFields();//获取实体类所有字段


        for (Field field : fields) {//添加所有字段eq查询
            field.setAccessible(true);
            String name =  field.getName();
            Object value = null;
            try {
                value = field.get(t);//获取value值
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (field.isAnnotationPresent(TableId.class)) {
                map.put("id", Tran.toUnderLine(name));
                map.put("idValue", String.valueOf(value));
            }

            Bo bo = null;
            if (field.isAnnotationPresent(Bo.class)) {
                bo = field.getDeclaredAnnotation(Bo.class);
            }

            boolean isVo = false;
            if (bo == null) {
                isVo = true;
            } else {
                isVo = bo.exist();
            }

            if (isVo){//处理字段
                if (bo != null && value != null){
                    regexValue(value, bo);//正则匹配
                    voHandleUpdate(t, mapper, field, bo, value, name, map);//处理功能
                }
            }else{
                try {
                    field.set(t, null);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return t;
    }
    /**
     *
     * @param t 处理对象
     * @param mapper 数据库mapper
     * @param field 字段
     * @param bo bo注解
     * @param value 字段value值
     * @param name 字段名
     * @param <T>
     * @throws BoException
     */
    public static <T, M extends BaseMapper<T>> void voHandleUpdate(T t , M mapper, Field field,Bo bo, Object value, String name, Map<String, String> map) throws BoException {
        BoType [] boTypes = bo.type();
        for (BoType boType : boTypes) {
            switch (boType){
                case NOT_STR:
                    if (value == null || "".equals(String.valueOf(value).trim())){
                        throw new BoException("存在NULL或空字符数据的字段--> " + name);
                    }
                    break;
                case REPEAT:
                    QueryWrapper<T> wrapper = new QueryWrapper<>();
                    wrapper.ne(map.get("id"), map.get("idValue"));
                    wrapper.eq(Tran.toUnderLine(name), value);
                    if (mapper.exists(wrapper)) {
                        throw new BoException("字段 [ " + name + " ] 存在重复的数据--> " + value);
                    }
                    break;
                case FILTER://特殊字符串处理
                    try {
                        if (value instanceof String){
                            field.set(t, FilterStr.stringFilter(String.valueOf(value)));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    }

    /**
     *
     * @param value 匹配内容
     * @param bo bo bo注解
     * @throws VoException
     */
    private static void regexValue(Object value, Bo bo) throws BoException {
        if (bo.exist() && value != null){//对字段value进行处理
            String regex = bo.regex();//正则匹配
            if (!"".equals(regex)){
                boolean isMatch = Pattern.matches(regex, String.valueOf(value));
                if (!isMatch){
                    throw new BoException(bo.msg());
                }
            }
        }
    }


}
