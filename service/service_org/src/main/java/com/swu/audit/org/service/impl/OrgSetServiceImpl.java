package com.swu.audit.org.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swu.audit.model.org.OrgSet;
import com.swu.audit.org.mapper.OrgSetMapper;
import com.swu.audit.org.service.OrgSetService;
import org.springframework.stereotype.Service;

@Service
public class OrgSetServiceImpl extends ServiceImpl<OrgSetMapper, OrgSet> implements OrgSetService {
}
