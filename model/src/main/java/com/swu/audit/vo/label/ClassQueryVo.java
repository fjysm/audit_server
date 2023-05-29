package com.swu.audit.vo.label;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ClassQueryVo {
    @ApiModelProperty(value = "标签关键字")
    private String classKeyWords;
}
