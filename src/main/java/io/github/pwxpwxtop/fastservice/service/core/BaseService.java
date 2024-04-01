package io.github.pwxpwxtop.fastservice.service.core;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pwxpwxtop.fastservice.dao.Dao;
import io.github.pwxpwxtop.fastservice.exception.BoException;
import io.github.pwxpwxtop.fastservice.exception.VoException;
import io.github.pwxpwxtop.fastservice.listener.ModelListener;
import io.github.pwxpwxtop.fastservice.model.ExpPro;
import io.github.pwxpwxtop.fastservice.model.ImpPro;
import io.github.pwxpwxtop.fastservice.utils.BoUtils;
import io.github.pwxpwxtop.fastservice.utils.ExcelUtils;
import io.github.pwxpwxtop.fastservice.utils.FieldUtils;
import io.github.pwxpwxtop.fastservice.utils.VoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import io.github.pwxpwxtop.fastservice.service.Service;
import io.github.pwxpwxtop.fastservice.r.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public abstract class BaseService<T, M extends BaseMapper<T>> implements Service<T> {

    @Autowired
    private M mapper;

    @Autowired
    private Dao dao;

    @Override
    public R<T> data(T t , Page<T> page) {
        QueryWrapper<T> wrapper = null;
        try {
            wrapper = VoUtils.getQueryWrapper(t);
            mapper.selectPage(page, wrapper);
        } catch (VoException e) {
            e.printStackTrace();
            return R.no(e.getMessage());
        }
         return R.ok(page, "搜索成功");
    }

    @Override
    public R<T> insert(@RequestBody T t) {
        T t1 = null;
        try {
            t1 = BoUtils.getModel(t, mapper);
        } catch (BoException e) {
            e.printStackTrace();
            return R.no(e.getMessage());
        }
        int count = 0;
        if ((count = mapper.insert(t1)) == 0){
            return R.no("添加失败");
        }
        return R.ok(Long.valueOf(count), "添加成功");
    }

    @Override
    public R<T> update(@RequestBody T t) {
        T t1 = null;
        try {
            t1 = BoUtils.getModelUpdate(t, mapper);
        } catch (BoException e) {
            e.printStackTrace();
            return R.no(e.getMessage());
        }
        int count = 0;
        if ((count = mapper.updateById(t1)) == 0){
            return R.no("更新失败");
        }
        return R.ok(Long.valueOf(count), "更新成功");
    }

    @Override
    public R<T> delete(@RequestBody T t) {
        int count = mapper.deleteById(t);
        if (count == 0){
            return R.no("删除失败");
        }
        return R.ok(Long.valueOf(count), "删除成功");
    }

    @Override
    public R<T> deletes(@RequestBody List<String> ids) {
        long count = 0;
        try {
            count = mapper.deleteBatchIds(ids);
        }catch (Exception e){
            e.printStackTrace();
            return R.no(e.getMessage());
        }

        return R.ok("成功删除" + count + "条数据");
    }

    @Override
    public R<T> impExcel(MultipartFile file, T t, ImpPro<T> impPro) {
        ModelListener<T, M> modelListener = null;
        try {
            modelListener = new ModelListener<>(dao, mapper, impPro);
            ExcelUtils.impData(file, t, modelListener);
        } catch (IOException e) {
            e.printStackTrace();
            return R.no("io异常");
        }
        return R.ok(modelListener.getErrList(), "导入成功" + modelListener.getTotal() + "条数据");
    }

    @Override
    public void expExcel(@RequestBody ExpPro expPro, HttpServletResponse response,   T t) {
        try {
            ExcelUtils.expData(response, expPro, mapper, t);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
