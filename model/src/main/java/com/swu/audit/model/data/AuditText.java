package com.swu.audit.model.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.swu.audit.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "审计文本设置")
@TableName("audit_text")
public class AuditText extends BaseEntity {

    @ApiModelProperty(value = "审计文本")
    @TableField("text")
    private String text;

    @ApiModelProperty(value = "数据集id")
    @TableField("dataset_id")
    private Integer datasetId;

    @ApiModelProperty(value = "分类状态")
    @TableField("status")
    private String status;



    @ApiModelProperty(value = "分类标签id")
    @TableField("class_id")
    private Integer classId;

    @ApiModelProperty(value = "确认类型")
    @TableField("confirm_type")
    private String confirmType;

    @ApiModelProperty(value = "公司")
    @TableField("company")
    private String company;
}
