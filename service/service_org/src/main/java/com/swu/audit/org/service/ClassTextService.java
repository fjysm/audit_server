package com.swu.audit.org.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swu.audit.model.data.ClassText;
import com.swu.audit.vo.auditText.ClassResultVo;
import com.swu.audit.vo.auditText.checkClassResultVo;
import com.swu.audit.vo.user.LoginVo;

import java.util.Map;

public interface ClassTextService extends IService<ClassText> {

    ClassResultVo getClassResult(Integer id);

    checkClassResultVo getCheckClassResult(Integer id);
}
