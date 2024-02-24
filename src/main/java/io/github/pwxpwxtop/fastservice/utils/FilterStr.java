package io.github.pwxpwxtop.fastservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * @author pwx
 * @version 1.0
 * @describe sql字符串过滤
 * @date 2022/11/12 16:44
 */
public class FilterStr {

    //过滤特殊字符
    public static String stringFilter(String str) throws PatternSyntaxException {
        if (str == null){
            return null;
        }
        // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regex="(?i)(--|select|create|delete|update|drop|from|and|or|—|[` ~!@#$%^&*+=|{}()';',.<>?~！@#￥%……&*（）+|{}【】‘；：”“’。 ，、？])";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.replaceAll(" ").trim();
    }




}
