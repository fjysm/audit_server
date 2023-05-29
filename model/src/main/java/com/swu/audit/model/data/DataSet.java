package com.swu.audit.model.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.swu.audit.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "审计文本数据集设置")
@TableName("data_set")
public class DataSet extends BaseEntity {

    @ApiModelProperty(value = "数据集名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "数据量")
    @TableField("data_count")
    private Integer dataCount;

    @ApiModelProperty(value = "备注")
    @TableField("comment")
    private String comment;

    @ApiModelProperty(value = "未分类数量")
    @TableField("unclassify_count")
    private Integer unclassifyCount;

    @ApiModelProperty(value = "待分类数量")
    @TableField("pending_classify_count")
    private Integer pendingClassifyCount;

    @ApiModelProperty(value = "已分类数量")
    @TableField("classify_count")
    private Integer classifyCount;
}
