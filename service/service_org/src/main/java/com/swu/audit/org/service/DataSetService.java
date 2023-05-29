package com.swu.audit.org.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swu.audit.model.data.DataSet;
import com.swu.audit.org.mapper.DataSetMapper;

import javax.annotation.Resource;
import java.util.Map;

public interface DataSetService extends IService<DataSet> {

    Map<String, Integer> getSumOfThreeColumns();



}
