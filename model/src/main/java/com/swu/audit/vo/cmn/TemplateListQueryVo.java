package com.swu.audit.vo.cmn;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TemplateListQueryVo {
    @ApiModelProperty(value = "审计模板名称")
    private String templateName;

    @ApiModelProperty(value = "审计模板编号")
    private String templateCode;
}
