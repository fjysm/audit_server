package com.swu.audit.vo.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserInfoQueryVo {
    @ApiModelProperty(value = "用户名")
    private String username;
}
