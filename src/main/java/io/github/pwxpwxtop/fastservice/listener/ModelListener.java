package io.github.pwxpwxtop.fastservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.pwxpwxtop.fastservice.dao.Dao;
import io.github.pwxpwxtop.fastservice.exception.BoException;
import io.github.pwxpwxtop.fastservice.model.ImpPro;
import io.github.pwxpwxtop.fastservice.utils.BoUtils;
import io.github.pwxpwxtop.fastservice.utils.SqlUtils;
import io.github.pwxpwxtop.fastservice.utils.Tran;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;


public class ModelListener<T, M extends BaseMapper<T>> implements ReadListener<T> {


    private Dao dao = null;

    private M mapper = null;

    private ImpPro<T> impPro = null;
    /**
     * 缓存的个数
     */
    private static final int BATCH_COUNT = 100;


    private StringJoiner sql = null;

    private Long count = 0L;

    private Long total = 0L;//总条数

    private Long countNumber = 0L;//写入第n行数据

    private List<String> errList = null;

    public ModelListener(Dao dao, M mapper, ImpPro<T> impPro) {
        this.dao = dao;
        this.mapper = mapper;
        this.impPro = impPro;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        countNumber++;
        if (errList == null){
            errList = new ArrayList<>();
        }

        if (impPro.getIsBo()){//Bo验证数据
            try {
                t = BoUtils.getModel(t, mapper);
            } catch (BoException e) {
                e.printStackTrace();
                errList.add("第"+ countNumber +"行数据验证失败："+e.getMessage());
                return;
            }
        }

        if (count++ == 0){
            Class<?> tClass = t.getClass();
            String tableName = null;
            if (tClass.isAnnotationPresent(TableName.class)) {
                TableName name = tClass.getDeclaredAnnotation(TableName.class);
                tableName = name.value();
            }else {
                tableName = Tran.toUnderLine(tClass.getSimpleName());
            }
            StringJoiner joinerKey = new StringJoiner(" , ", "( ", " )");
            SqlUtils.sqlKey(t, t.getClass(), joinerKey);
            sql = new StringJoiner(" , ", "insert into " + tableName + " " + joinerKey + " values ", "");
        }
        StringJoiner joinerValue = SqlUtils.getInsertSql(t);
        sql.add(joinerValue.toString());
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (count > BATCH_COUNT) {
            saveData();
            count = 0L;
        }
        total++;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveData();
    }

    // 加上存储数据库
    private void saveData() {
        if (sql != null){
            dao.sql(sql.toString());
        }

    }

    public Long getTotal(){
        return total;
    }

    public List<String> getErrList(){
        return errList;
    }
}
