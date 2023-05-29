package com.swu.audit.vo.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "近7天上传的审计文本数量")
public class Bar {
    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "数量")
    private Integer count;
}
