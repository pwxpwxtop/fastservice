package io.github.pwxpwxtop.fastservice.service.core;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pwxpwxtop.fastservice.model.ExpPro;
import io.github.pwxpwxtop.fastservice.model.ImpPro;
import io.github.pwxpwxtop.fastservice.r.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class FastService<T ,  M extends BaseMapper<T>> extends BaseService<T, M> {

    @Override
    @GetMapping("/data")
    public R<T> data(T t, Page<T> page) {
        return super.data(t, page);
    }

    @Override
    @PostMapping("/insert")
    public R<T> insert(T t) {
        return super.insert(t);
    }

    @Override
    @PostMapping("/update")
    public R<T> update(T t) {
        return super.update(t);
    }

    @Override
    @PostMapping("/delete")
    public R<T> delete(T t) {
        return super.delete(t);
    }

    @Override
    @PostMapping("/deletes")
    public R<T> deletes(List<String> ids) {
        return super.deletes(ids);
    }

    @Override
    @PostMapping("/impExcel")
    public R<T> impExcel(MultipartFile file, T t, ImpPro<T> impPro) {
        return super.impExcel(file, t, impPro);
    }

    @Override
    @PostMapping("/expExcel")
    public void expExcel(ExpPro expPro, HttpServletResponse response, T t) {
        super.expExcel(expPro, response, t);
    }
}
