
package io.github.pwxpwxtop.fastservice.r;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String OK = "OK";

    private static final String NO = "NO";

    private String code;  //返回状态,成功和失败
    private String msg; //报的错误异常消息
    private Object data; //需要数据
    private Long total; //表格数量
    private Long count; //更新的数据条数

    public static <T> R<T> ok(Page<T> page){
        return ok(page,  null);
    }

    public static <T> R <T> ok(Page<T> page, String msg){
        R<T> r = new R<>();
        r.setCode(OK);
        r.setCount(r.getCount());
        r.setData(page.getRecords());
        r.setMsg(msg);
        r.setTotal(page.getTotal());
        return r;
    }

    public static <T> R<T> ok(Object obj, String msg, Long count, Long total){
        R<T> r = new R<>();
        r.setCode(OK);
        r.setCount(count);
        r.setData(obj);
        r.setMsg(msg);
        r.setTotal(total);
        return r;
    }

    //成功的消息
    public static <T> R<T> ok(){
        return ok(null, null, null, null);
    }

    public static <T> R<T> ok(String msg){
        return ok(null, msg, null, null);
    }

    //成功的消息,count 数据记录条数
    public static <T> R<T> ok(Long count){
        return ok(null, null, count, null);
    }
    public static <T> R<T> ok(Long count, String msg){
        return ok(null, msg,  count, null);
    }

    public static <T> R<T> ok(Object obj){
        return ok(obj, null, null, null);
    }

    public static <T> R<T> ok(Object obj, String msg){
        return ok(obj, msg, null, null);
    }

    public static <T> R <T> no(){
        return no(null, null);
    }

    public static <T> R <T> no(String msg){
        return no(null, msg);
    }

    public static  <T> R<T> no(Object data,  String msg){
        R<T> r = new R<>();
        r.setCode(NO);
        r.setMsg(msg);
        return r;
    }

    //工具类,返回解析后的map
    private static Map<String, Object> returnMap(R r){
        Map<String, Object> maps = new HashMap<>();
        Field [] fields =  r.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object obj = null;
            try {
                obj = fields[i].get(r);
                if (obj != null){
                    maps.put(fields[i].getName(), obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return maps;
    }
}
