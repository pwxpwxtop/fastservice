package io.github.pwxpwxtop.fastservice.utils;

import io.github.pwxpwxtop.fastservice.enums.DatabaseType;
import io.github.pwxpwxtop.fastservice.exception.TemplateException;
import io.github.pwxpwxtop.fastservice.model.jdbc.Jdbc;
import javafx.application.Application;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Description:    代码生成器
 * Author:         PWX
 * CreateDate:     2024/4/1 23:16
 */
public class GenCodeUtils {

    private static String selectIndex = "1";

    public static void genCode(Class<?> cls, String folder, String packageName){
        genCode(cls, null, folder, packageName);
    }

    public static void genCode(Class<?> cls, Jdbc jdbc, String folder, String packageName){
        String name = cls.getSimpleName();
        String parent = cls.getPackage().getName();
        String modelEntity = parent.substring(parent.lastIndexOf(".") + 1);
        Map<String, String> contentMap = getTemplateContent();
        for (String key : contentMap.keySet()) {
            StringBuilder stringBuilder = new StringBuilder(contentMap.get(key));
            replaceAll(stringBuilder, "name", name);
            replaceAll(stringBuilder, "packageName", packageName);
            replaceAll(stringBuilder, "model", modelEntity);
            replaceAll(stringBuilder, "datetime", getDate());
            replaceAll(stringBuilder, "nameLow", name.toLowerCase(Locale.ROOT));
            genFile(folder, stringBuilder.toString(), key, name);
        }

        if (jdbc != null){//生成数据
            genDatabase(cls, jdbc);
        }
    }

    public static void genDatabase( Class<?> cls, Jdbc jdbc)  {
        try {
            //加载驱动
            Class.forName(jdbc.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("数据库驱动加载失败");
            return;
        }

        //连接数据库
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbc.getUrl(),jdbc.getUsername(),jdbc.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("数据库连接失败");
            return;
        }
        //执行SQL对象Statement，执行SQL的对象
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("创建Statement对象失败");
            return;
        }

        String sql = SqlUtils.getCreateTable(cls, jdbc.getDatabaseType());
        System.out.println("执行sql语句: "+sql);
        try {
            boolean isExists = statement.execute(sql);
            if (isExists){
                System.out.println("数据库表已存在");
            }else{
                System.out.println("数据库表创建成功" );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void genFile(String folder, String content, String key, String fileName){
        File folderPath  = new File(folder);
        if (!folderPath.exists()) {
            folderPath.mkdirs();
        }
        String pathFile = folder;
        switch (key){
            case "mapper":
                pathFile = folder + "/mapper/" + fileName + "Mapper.java";
                break;
            case "service":
                pathFile = folder + "/service" + "/" + fileName + "Service.java";
                break;
            default:
                pathFile = folder + "/controller" + "/" + fileName + "Controller.java";
        }
        File file = new File(pathFile);

        File filePath = file.getParentFile();
        if (!filePath.exists()){
            filePath.mkdirs();
        }
        if (!file.exists()){
            try {
                file.createNewFile();
                selectIndex = "0";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("===============================文件选择====================================");
            System.out.println("生成文件: " + pathFile.replaceAll("\\\\", "/"));
            System.out.println("文件已存在, 书否进行覆盖: 1.覆盖当前文件 2.全部覆盖 3.退出");
            Scanner input = new Scanner(System.in);
            System.out.print("请输入你的选择: ");
            if (!"2".equals(selectIndex)){
                selectIndex = input.next();
            }
            System.out.println();
            System.out.println("=========================================================================");
        }

        switch (selectIndex){
            case "0":
            case "1":
                writeContent(file, content);
                break;
            case "2":
                writeContent(file, content);
                selectIndex = "2";
                break;
            default:
                System.out.println("已经退出");
        }

    }

    private static void writeContent(File file, String content) {
        try {
            Writer writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            System.out.println("文件生成成功:" + file.getPath());
            System.out.println("\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //获取当前时间
    private static String getDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    private static void replaceAll(StringBuilder sb, String keys,String content){
        int t;
        String keyContent = "${" + keys + "}";
        if ((t = sb.indexOf(keyContent))!= -1){//修改code的内容
            sb.replace(t, t + keyContent.length(), content);
            replaceAll(sb, keys, content);
        }else {
            return;
        }
    }



    private static Map<String, String> getTemplateContent(){
        URL location = GenCodeUtils.class.getProtectionDomain().getCodeSource().getLocation();//获得当前的URL
        URL mapperPath = GenCodeUtils.class.getResource("/codeTemplate/Mapper.txt");
        URL servicePath = GenCodeUtils.class.getResource("/codeTemplate/Service.txt");
        URL controllerPath = GenCodeUtils.class.getResource("/codeTemplate/Controller.txt");
        Map<String, String> mapCode = new HashMap<>();
        mapCode.put("mapper", getFileCode(mapperPath));
        mapCode.put("service", getFileCode(servicePath));
        mapCode.put("controller", getFileCode(controllerPath));
        return mapCode;
    }


    private static String getFileCode(URL url){
        if (url == null){
            try {
                throw new TemplateException("丢失模板文件");
            } catch (TemplateException ex) {
                ex.printStackTrace();
            }
            return "";
        }
        InputStream is = null;
        try {
            is = url.openStream();
            byte [] bytes = null;
            bytes = new byte[is.available()];
            is.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (is != null){
                is.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getFileCode(File file){
        if (file.exists() && file.isFile()){
            try {
                Reader reader = new FileReader(file);
                try {
                    char [] chars = new char[(int)file.length()];
                    reader.read(chars);
                    return String.valueOf(chars);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                throw new TemplateException("丢失模板文件:" + file.getPath());
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
        return "";
    }





}
