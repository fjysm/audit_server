package com.swu.audit.org.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swu.audit.model.data.DataSet;
import com.swu.audit.model.org.OrgSet;
import com.swu.audit.org.mapper.DataSetMapper;
import com.swu.audit.org.mapper.OrgSetMapper;
import com.swu.audit.org.service.DataSetService;
import com.swu.audit.org.service.OrgSetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class DataSetServiceImpl extends ServiceImpl<DataSetMapper, DataSet> implements DataSetService {
    @Resource
    private DataSetMapper dataSetMapper;

    public Map<String, Integer> getSumOfThreeColumns() {
        return dataSetMapper.sumOfThreeColumns();
    }
}
