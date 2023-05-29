package com.swu.audit.model.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.swu.audit.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "待分类文本表")
@TableName("class_text")
public class ClassText extends BaseEntity {
    @ApiModelProperty(value = "审计文本id")
    @TableField("text_id")
    private Integer textId;

    @ApiModelProperty(value = "分类标签id")
    @TableField("class_id")
    private Integer classId;

    @ApiModelProperty(value = "是否反馈")
    @TableField("is_confirm")
    private String isConfirm;

    @ApiModelProperty(value = "分类类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "推荐度")
    @TableField("recommend")
    private String recommend;




}
