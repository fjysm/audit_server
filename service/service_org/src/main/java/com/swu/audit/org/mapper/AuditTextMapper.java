package com.swu.audit.org.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swu.audit.model.data.AuditText;
import com.swu.audit.org.mapper.provider.AuditSqlProvider;
import com.swu.audit.vo.auditText.AuditClassResultVo;
import com.swu.audit.vo.auditText.ClassResultVo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AuditTextMapper extends BaseMapper<AuditText> {

    @SelectProvider(type = AuditSqlProvider.class,method = "selectAuditClassList")
    @Results({
            @Result(column = "text", property = "auditText"),
            @Result(column = "first_class", property = "firstClass"),
            @Result(column = "second_class", property = "secondClass"),
            @Result(column = "third_class", property = "thirdClass"),
            @Result(column = "four_class", property = "fourClass")
    })
    List<AuditClassResultVo> selectAuditClassList(@Param("page")IPage<AuditClassResultVo> page, @Param("id") Integer id, @Param("status") String status,@Param("keyword") String keyword);

    @Select("SELECT DATE(create_time) AS day, COUNT(*) AS count " +
            "FROM audit_text " +
            "WHERE create_time >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
            "GROUP BY day")
    @Results({
            @Result(column = "day", property = "day", javaType = Date.class),
            @Result(column = "count", property = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> countDataByDay();

    @Select("SELECT DATE(update_time) AS day, confirm_type, COUNT(*) AS count " +
            "FROM audit_text " +
            "WHERE update_time >= DATE_SUB(CURDATE(), INTERVAL 6 DAY) " +
            "GROUP BY day, confirm_type")
    @Results({
            @Result(column = "day", property = "day", javaType = Date.class),
            @Result(column = "confirm_type", property = "confirm_type", javaType = String.class),
            @Result(column = "count", property = "count", javaType = Integer.class)
    })
    List<Map<String, Object>> countByDayAndClassifyType();

    @Select("SELECT confirm_type, COUNT(*) AS count " +
            "FROM audit_text " +
            "GROUP BY confirm_type")
    @Results({
            @Result(column = "confirm_type", property = "confirm_type", javaType = String.class),
            @Result(column = "count", property = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getAccurate();

    @Select("SELECT company, COUNT(*) AS count " +
            "FROM audit_text " +
            "GROUP BY company")
    @Results({
            @Result(column = "company", property = "company", javaType = String.class),
            @Result(column = "count", property = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getCompany();

}
