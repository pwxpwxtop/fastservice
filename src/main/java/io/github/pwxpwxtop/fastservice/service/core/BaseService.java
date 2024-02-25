package io.github.pwxpwxtop.fastservice.service.core;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.pwxpwxtop.fastservice.exception.BoException;
import io.github.pwxpwxtop.fastservice.exception.VoException;
import io.github.pwxpwxtop.fastservice.utils.BoUtils;
import io.github.pwxpwxtop.fastservice.utils.VoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import io.github.pwxpwxtop.fastservice.service.Service;
import io.github.pwxpwxtop.fastservice.r.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.annotation.Resource;





public abstract class BaseService<T, M extends BaseMapper<T>> implements Service<T> {

    @Autowired
    public M mapper;

    @Override
    @GetMapping("/data")
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
    @PostMapping("/insert")
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
    @PostMapping("/update")
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
    @PostMapping("/delete")
    public R<T> delete(@RequestBody T t) {
        int count = mapper.deleteById(t);
        if (count == 0){
            return R.no("删除失败");
        }
        return R.ok(Long.valueOf(count), "删除成功");
    }

}
