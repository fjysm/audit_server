package com.swu.audit.vo.org;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OrgSetQueryVo {

    @ApiModelProperty(value = "审计机构名称")
    private String orgname;

    @ApiModelProperty(value = "审计机构编号")
    private String orgcode;
}
