package com.swu.audit.org.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.swu.audit.model.data.DataSet;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface DataSetMapper extends BaseMapper<DataSet>{

    @Results({
            @Result(column = "sum1", property = "sum1", javaType = Integer.class),
            @Result(column = "sum2", property = "sum2", javaType = Integer.class),
            @Result(column = "sum3", property = "sum3", javaType = Integer.class),
    })
    @Select("SELECT SUM(unclassify_count) AS sum1, SUM(pending_classify_count) AS sum2, SUM(classify_count) AS sum3 FROM data_set")
    Map<String, Integer> sumOfThreeColumns();
}
