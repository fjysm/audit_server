package com.swu.audit.model.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.swu.audit.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "数据集导入历史表")
@TableName("import_history")
public class ImportHistory extends BaseEntity {

    @ApiModelProperty(value = "用户")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "数据集id")
    @TableField("dataset_id")
    private Integer datasetId;

    @ApiModelProperty(value = "导入类型")
    @TableField("import_type")
    private String importType;

    @ApiModelProperty(value = "数据量")
    @TableField("data_count")
    private Integer dataCount;

}
