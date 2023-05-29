package com.swu.audit.vo.auditText;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "已分类审计文本和分类结果")
public class AuditClassResultVo {

    @ApiModelProperty(value = "审计文本")
    private String auditText;


    @ApiModelProperty(value = "一级标签")
    private String firstClass;

    @ApiModelProperty(value = "二级标签")
    private String secondClass;

    @ApiModelProperty(value = "三级标签")
    private String thirdClass;

    @ApiModelProperty(value = "四级标签")
    private String fourClass;
}
