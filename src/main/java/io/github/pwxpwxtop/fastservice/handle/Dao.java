package io.github.pwxpwxtop.fastservice.handle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @description: 处理
 * @author: pwx
 * @data: 2022/11/4 20:52
 * @version: 1.0
 */
public class Dao<T> {


    private T e;


    public Dao() {

    }

    public Dao<T> unique(String column, String msg){
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        return this;
    }

    public void size(String column, String minLen, String maxLen){

    }

}
