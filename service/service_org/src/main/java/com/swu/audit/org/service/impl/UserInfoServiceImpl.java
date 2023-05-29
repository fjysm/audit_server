package com.swu.audit.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swu.audit.common.exception.YyghException;
import com.swu.audit.common.helper.JwtHelper;
import com.swu.audit.common.result.ResultCodeEnum;
import com.swu.audit.model.User.UserInfo;
import com.swu.audit.org.mapper.OrgSetMapper;
import com.swu.audit.org.mapper.UserInfoMapper;
import com.swu.audit.org.service.UserInfoService;
import com.swu.audit.vo.user.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends
        ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    private OrgSetMapper orgSetMapper;



    @Override
    public Map<String, Object> loginUser(LoginVo loginVo){
        String userAccount  = loginVo.getUserAccount();
        String password = loginVo.getPassword();
        //查询密码是否一致
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_account", userAccount);
        UserInfo userInfo = baseMapper.selectOne(wrapper);
        if(!password.equals(userInfo.getPassword())){
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getUserName();
        map.put("user", userInfo);
        String token = JwtHelper.createToken(Long.valueOf(userInfo.getId()),userInfo.getUserName());
        map.put("token", token);
        return map;


    }



}

