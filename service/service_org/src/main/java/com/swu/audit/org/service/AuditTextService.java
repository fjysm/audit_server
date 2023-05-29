package com.swu.audit.org.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.swu.audit.model.data.AuditText;
import com.swu.audit.vo.auditText.AuditClassResultVo;
import com.swu.audit.vo.auditText.ClassResultVo;

import java.util.List;
import java.util.Map;

public interface AuditTextService extends IService<AuditText> {
    List<String> classify(String text);

    List<AuditClassResultVo> selectAuditClassList(Integer id, IPage<AuditClassResultVo> page, String keyword);

    List<Map<String, Object>> getDataCountByDay();

    List<Map<String, Object>> countByDayAndClassifyType();

    List<Map<String, Object>> getAccurate();

    List<Map<String, Object>> getCompany();
}
