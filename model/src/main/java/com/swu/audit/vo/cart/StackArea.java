package com.swu.audit.vo.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "近7天每日分类数量和反馈数量")
public class StackArea {
    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "数量")
    private Integer count;
}
