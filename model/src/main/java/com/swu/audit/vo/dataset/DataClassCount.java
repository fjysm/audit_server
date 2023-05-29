package com.swu.audit.vo.dataset;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataClassCount {

    @ApiModelProperty(value = "未分类数量")
    private Integer unClassifyCount;

    @ApiModelProperty(value = "待分类数量")
    private Integer pendingClassifyCount;

    @ApiModelProperty(value = "已分类数量")
    private Integer classifyCount;

}

