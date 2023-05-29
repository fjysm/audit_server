package com.swu.audit.org.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swu.audit.model.User.UserInfo;
import com.swu.audit.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {

    Map<String, Object> loginUser(LoginVo loginVo);

}
