package io.github.pwxpwxtop.fastservice.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Tran {

    public static String change(String val){
        return val;
    }

    public static String toHump(String line){
        Matcher matcher = Pattern.compile("(?<=_)[a-zA-Z]").matcher(line.trim());
        while (matcher.find()) {
            String str = matcher.group();
            line = line.replace( "_" + str, str.toUpperCase());
        }
        return line;
    }


    public static String toUnderLine(String line){
        Matcher matcher = Pattern.compile("[A-Z]").matcher(line.trim());
        while (matcher.find()) {
            String str = matcher.group();
            line = line.replace(str, "_" + str.toLowerCase());
        }
        return line;
    }


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
