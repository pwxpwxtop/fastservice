package io.github.pwxpwxtop.fastservice.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pwxpwxtop.fastservice.model.ExpPro;
import io.github.pwxpwxtop.fastservice.model.ImpPro;
import io.github.pwxpwxtop.fastservice.r.R;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface Service<T> {

    R<T> data(T t, Page<T> page);

    //添加
    R<T> insert(T t);

    //更新
    R<T> update(T t);

    //删除
    R<T> delete(T t);

    //批量删除
    R<T> deletes(List<String> ids);

    //导入
    R<T> impExcel(MultipartFile file, T t,  ImpPro<T> impPro);

    //导出
    void expExcel(ExpPro expPro, HttpServletResponse response, T t);
}
