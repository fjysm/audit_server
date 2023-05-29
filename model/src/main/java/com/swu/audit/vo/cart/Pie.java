package com.swu.audit.vo.cart;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "各公司文本数量")
public class Pie {
    @ApiModelProperty(value = "公司")
    private String company;

    @ApiModelProperty(value = "数量")
    private Integer count;
}
