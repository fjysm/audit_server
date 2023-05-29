package com.swu.audit.org.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swu.audit.model.data.AuditText;
import com.swu.audit.model.data.LabelClass;
import com.swu.audit.org.mapper.AuditTextMapper;
import com.swu.audit.org.mapper.ClassMapper;
import com.swu.audit.org.service.AuditTextService;
import com.swu.audit.org.service.ClassService;
import org.springframework.stereotype.Service;

@Service
public class ClassServiceImpl extends ServiceImpl<ClassMapper, LabelClass> implements ClassService {
}
