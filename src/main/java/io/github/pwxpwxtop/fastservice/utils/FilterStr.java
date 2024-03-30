package io.github.pwxpwxtop.fastservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class FilterStr {

    //过滤特殊字符
    public static String stringFilter(String str) throws PatternSyntaxException {
        if (str == null){
            return null;
        }
        // 清除掉所有特殊字符
        String regex="(?i)(-|select|create|delete|update|drop|from|and|or|—|[-` ~!@#$%^&*+=|{}()';',.<>?~！@#￥%……&*（）+|{}【】‘；：”“’。 ，、？])";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.replaceAll(" ").trim();
    }




}
