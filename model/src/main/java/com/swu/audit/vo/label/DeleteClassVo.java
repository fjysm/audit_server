package com.swu.audit.vo.label;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DeleteClassVo {
    @ApiModelProperty(value = "批量删除标签数组id")
    private ArrayList<Integer> ids;
}
