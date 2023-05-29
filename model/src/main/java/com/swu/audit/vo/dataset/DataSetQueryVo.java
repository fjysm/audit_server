package com.swu.audit.vo.dataset;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataSetQueryVo {
    @ApiModelProperty(value = "数据集名称")
    private String dataSetName;

    @ApiModelProperty(value = "数据集类型")
    private String type;
}
