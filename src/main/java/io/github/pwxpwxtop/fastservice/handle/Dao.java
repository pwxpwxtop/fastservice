package io.github.pwxpwxtop.fastservice.handle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


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
