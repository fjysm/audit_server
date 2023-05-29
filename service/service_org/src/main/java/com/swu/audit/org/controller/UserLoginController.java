package com.swu.audit.org.controller;

import com.swu.audit.common.result.Result;
import com.swu.audit.org.service.UserInfoService;
import com.swu.audit.vo.user.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Api(tags = "用户登录")
@RestController
@RequestMapping("/api/user")
@CrossOrigin(allowCredentials="true")
public class UserLoginController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String, Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }
}
