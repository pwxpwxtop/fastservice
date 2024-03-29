package io.github.pwxpwxtop.fastservice.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pwxpwxtop.fastservice.model.ExpPro;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class ExcelUtils<T>{

    public ExcelUtils() {
    }

    public static <T> void impData(MultipartFile file, T obj, ReadListener listener) throws IOException {
        byte [] by = file.getBytes();
        InputStream is = new ByteArrayInputStream(by);
        EasyExcel.read(is, obj.getClass(), listener).sheet().doRead();
    }


    //导出数据
    public static <T, M extends BaseMapper<T>> void expData(HttpServletResponse response, ExpPro expPro, M mapper, T t) throws IOException {
        List<String> columns = expPro.getColumns();//要查询的列
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        List<List<String>> listHead = null;
        //查询指定的列
        if (columns.size() != 0){
            String [] col = new String[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                col[i] = columns.get(i);
            }
            queryWrapper.select(col);
            listHead = getHeads(t.getClass(), columns);
        }

        List<T> list = null;
        long time = new Date().getTime();
        String filePath = System.getProperty("user.home") + "/Downloads/" + time;
        if (expPro.getIsSubTable()){//是否开启分页打包
            if (!expPro.getIsAll()){
                queryWrapper.in(expPro.getIdName(), expPro.getIds());
            }
            Long maxSize = expPro.getTableSize();
            for (int i = 0; true; i++) {
                //分页查询数据
                Page<T> page = new Page<>();
                page.setCurrent(i + 1);
                page.setSize(maxSize);

                mapper.selectPage(page, queryWrapper);//查询
                list = page.getRecords();
                if (list.size() == 0){
                    break;
                }
                String fileName = filePath + "/" + expPro.getFileName() + i + ".xlsx";
                File file = new File(filePath);
                if (!file.exists()){
                    file.mkdirs();
                }
                OutputStream outputStream = new FileOutputStream(fileName);
                //写入文件中
                EasyExcel.write(fileName, t.getClass()).head(listHead).sheet("Sheet1").doWrite(reData(list, columns));//写入文件中
                outputStream.close();
            }
            //发送zip文件
            downloadExcelZip(response, filePath, expPro.getFileName());
        }else{

            if (expPro.getIsAll()){//查询全部
                list = mapper.selectList(queryWrapper);
            }else{//根据id查询
                queryWrapper.in(expPro.getIdName(), expPro.getIds());
                list = mapper.selectList(queryWrapper);
            }
            downloadExcel(response, t, reData(list, columns), expPro.getFileName(), listHead);
        }
    }

    public static <T> void downloadExcel(HttpServletResponse response, T t, List<List<Object>> data, String fileName, List<List<String>> listHead){
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系

        try {
            fileName = fileName == null ? "我的excel文件":fileName;
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        try {
            EasyExcel.write(response.getOutputStream(), t.getClass()).head(listHead).sheet("Sheet1").doWrite(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public  static void downloadExcelZip(HttpServletResponse response,String filePath, String fileName) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".zip", "UTF-8"));
        ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());//zos
        //待压缩文件/目录所在的目录
        File fileFolder = new File(filePath);
        //获取目录下的所有文件
        File[] files = fileFolder.listFiles();

        ZipEntry zipEntry;
        byte[] byteArray;
        int len;
        if (files == null){
           return;
        }
        for (File file : files) {
            //
            zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);
            //读取待压缩的文件并写入到zip输出流中
            try (FileInputStream in = new FileInputStream(file)) {
                byteArray = new byte[1024];
                while ((len = in.read(byteArray)) != -1) {
                    zos.write(byteArray, 0, len);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            zos.closeEntry();
        }
        zos.close();
        deleteFile(new File(filePath));
    }

    public static Boolean deleteFile(File file) {
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()) {
            return false;
        }
        File[] files = file.listFiles();
        if (files == null){
            return false;
        }

        //遍历该目录下的文件对象
        for (File f : files) {
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()) {
                //递归删除目录下的文件
                deleteFile(f);
            } else {
                //文件删除
                f.delete();
            }
        }
        //文件夹删除
        file.delete();
        return true;
    }

    public static List<List<String>> getHeads(Class<?> cls,  List<String> list){
        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<String> list1 = new ArrayList<>();
            list1.add(getHeads(cls, list.get(i)));
            lists.add(list1);
        }
        return lists;
    }



    private static String getHeads(Class<?> cls, String name) {
        Class<?> superClass = cls.getSuperclass();//获取父级类对象
        String myName = name;
        if (superClass != null){
            myName = getHeads(superClass, name);
        }
        Field[] fields =  cls.getDeclaredFields();//获取实体类所有字段
        for (Field field : fields) {//添加所有字段eq查询
            field.setAccessible(true);
            String fieldName =  field.getName();
            if (!fieldName.equals(name)){
                continue;
            }

            if (field.isAnnotationPresent(ExcelProperty.class)) {
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                String [] values = excelProperty.value();
                if (values.length > 0){
                    return values[0];
                }else {
                    return fieldName;
                }
            }else{
                return fieldName;
            }
        }
        return myName;
    }




    public static <T> List<List<Object>> reData(List<T> list, List<String> columnList){
        List<List<Object>> listArrayList = new ArrayList<>();
        for (T t : list) {

            Map<String, Object> map = objectToMap(t.getClass(), t,  new HashMap<>());
            List<Object> lists = new ArrayList<>();
            for (String s : columnList) {
                lists.add(map.get(s));
            }
            listArrayList.add(lists);
        }
        return listArrayList;
    }


    /**
     * 对象转map
     * @param cls 对象类
     * @param t 对象
     * @param map 转换的map
     * @param <T> 实体类对象
     * @return
     */
    private static <T> Map<String, Object> objectToMap(Class<?> cls, T t,  Map<String, Object> map) {
        Class<?> superClass = cls.getSuperclass();//获取父级类对象
        if (superClass != null){
            map = objectToMap(superClass,t, map);
        }
        Field[] fields =  cls.getDeclaredFields();//获取实体类所有字段
        for (Field field : fields) {//添加所有字段eq查询
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


}
