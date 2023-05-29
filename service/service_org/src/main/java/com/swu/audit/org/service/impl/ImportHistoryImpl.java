package com.swu.audit.org.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swu.audit.model.data.ImportHistory;
import com.swu.audit.model.data.LabelClass;
import com.swu.audit.org.mapper.ClassMapper;
import com.swu.audit.org.mapper.ImportHistoryMapper;
import com.swu.audit.org.service.ClassService;
import com.swu.audit.org.service.ImportHistoryService;
import org.springframework.stereotype.Service;

@Service
public class ImportHistoryImpl extends ServiceImpl<ImportHistoryMapper, ImportHistory> implements ImportHistoryService {
}
