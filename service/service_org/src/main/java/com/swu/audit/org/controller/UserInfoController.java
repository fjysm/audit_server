package com.swu.audit.org.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swu.audit.common.helper.JwtHelper;
import com.swu.audit.common.result.Result;
import com.swu.audit.common.password.RandomPassword;
import com.swu.audit.model.User.UserInfo;
import com.swu.audit.org.service.EmailService;
import com.swu.audit.org.service.UserInfoService;
import com.swu.audit.vo.user.LoginVo;
import com.swu.audit.vo.user.UserInfoQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/audit/user")
@CrossOrigin(allowCredentials = "true")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private EmailService emailService;

    @ApiOperation("用户信息")
    @GetMapping("info")
    public Result getUsrInfo(HttpServletRequest request) {
        String token = request.getHeader("authorization");
        Long userId = JwtHelper.getUserId(token);
        UserInfo userInfo = userInfoService.getById(userId);
        return Result.ok(userInfo);

    }

    @ApiOperation("获取所有用户信息")
    @GetMapping("getAllUser/{current}/{limit}")
    public Result getAllUsrInfo(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) UserInfoQueryVo userInfoQueryVo) {
        Page<UserInfo> page = new Page<>(current, limit);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        if(userInfoQueryVo != null){
            String username = userInfoQueryVo.getUsername();
            if (!StringUtils.isEmpty(username)) {
                queryWrapper.like("user_name", username);
            }
        }
        Page<UserInfo> userInfoList = userInfoService.page(page, queryWrapper);

        return Result.ok(userInfoList);

    }

    @ApiOperation("修改用户权限")
    @PostMapping("editRole")
    public Result getAllUsrInfo(@RequestBody UserInfo userInfo) {
       userInfo.setRole("admin");
       userInfoService.updateById(userInfo);
        return Result.ok();
    }

    @ApiOperation("修改用户密码")
    @PostMapping("EditPassword")
    public Result EditPassword(@RequestBody LoginVo loginVo) {
       UserInfo userInfo = userInfoService.getById(loginVo.getId());
       if(userInfo.getPassword().equals(loginVo.getPassword())){
           userInfo.setPassword(loginVo.getNewPassword());
           userInfoService.updateById(userInfo);
           return Result.ok();
       }else {
           return Result.fail();
       }
    }

    @ApiOperation("创建用户")
    @PostMapping("createUser")
    public Result createUser(@RequestBody UserInfo userInfo) {
        userInfo.setPassword(RandomPassword.generateRandomString(6));
        userInfo.setRole("user");
        boolean flag = userInfoService.save(userInfo);
        if(flag){
            emailService.sendEmail(userInfo.getEmail(), userInfo.getUserAccount(),userInfo.getPassword());
            return Result.ok();
        }else {
            return Result.fail();
        }

    }
}
