package com.swu.audit.vo.auditText;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AuditTextQueryVo {
    @ApiModelProperty(value = "关键字")
    private String keyWord;
}
