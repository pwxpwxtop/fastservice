package io.github.pwxpwxtop.fastservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author pwx
 * @version 1.0
 * @describe 下划线转驼峰法
 * @date 2022/9/17 9:13
 */
public class Tran {

    public static String change(String val){
        return val;
    }
    /**
     * 下划线转驼峰法
     * @return 转换后的字符串
     */
    public static String toHump(String line){
        Matcher matcher = Pattern.compile("(?<=_)[a-zA-Z]").matcher(line.trim());
        while (matcher.find()) {
            String str = matcher.group();
            line = line.replace( "_" + str, str.toUpperCase());
        }
        return line;
    }

    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String toUnderLine(String line){
        Matcher matcher = Pattern.compile("[A-Z]").matcher(line.trim());
        while (matcher.find()) {
            String str = matcher.group();
            line = line.replace(str, "_" + str.toLowerCase());
        }
        return line;
    }

    /**
     * 驼峰法转下划线
     * @param column 源字符串
     * @return 转换后的字符串
     */
    public static String transform(String column){
        column = column.trim();
        Matcher matcher = Pattern.compile("[A-Z]").matcher(column);
        while (matcher.find()) {
            String str = matcher.group();
            column = column.replace(str, "_" + str.toLowerCase());
        }
        return column;
    }

    


}
