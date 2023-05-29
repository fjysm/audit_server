package com.swu.audit.org.mapper.provider;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.swu.audit.vo.auditText.ClassResultVo;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class AuditSqlProvider {
    public String selectAuditClassList(Map<String, Object> params) {
        IPage<ClassResultVo> page = (IPage<ClassResultVo>) params.get("page");
        Integer id = (Integer) params.get("id");
        String status = (String) params.get("status");
        String keyword = (String) params.get("keyword");
        SQL sql = new SQL()
                .SELECT_DISTINCT("at.text", "c.first_class", "c.second_class", "c.third_class", "c.four_class")
                .FROM("audit_text at")
                .JOIN("class c ON at.class_id = c.id")
                .AND()
                .WHERE("at.status = #{status}")
                .AND()
                .WHERE("at.dataset_id = #{id}");
        if (keyword != null && !keyword.isEmpty()) {
             sql.AND().WHERE("(at.text LIKE CONCAT('%', #{keyword}, '%') OR c.first_class LIKE CONCAT('%', #{keyword}, '%') OR c.second_class LIKE CONCAT('%', #{keyword}, '%') OR c.third_class LIKE CONCAT('%', #{keyword}, '%') OR c.four_class LIKE CONCAT('%', #{keyword}, '%'))");

        }
//                .AND()
//                .WHERE("at.text like #{keyword}")
//                .OR()
//                .WHERE("c.first_class like #{keyword}")
//                .OR()
//                .WHERE("c.second_class like #{keyword}")
//                .OR()
//                .WHERE("c.third_class like #{keyword}")
//                .OR()
//                .WHERE("c.four_class like #{keyword}")

        return sql.toString();
    }
}
