package com.swu.audit.vo.auditText;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@Data
@ApiModel(description = "算法的分类结果")
public class RecommandResult {
    @ApiModelProperty(value = "分类结果列表")
    private List<String> classList;
}
