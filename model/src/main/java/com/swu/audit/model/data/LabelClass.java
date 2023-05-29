package com.swu.audit.model.data;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.swu.audit.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "分类标签设置")
@TableName("class")
public class LabelClass extends BaseEntity {


    @ApiModelProperty(value = "一级标签")
    @TableField("first_class")
    private String firstClass;

    @ApiModelProperty(value = "二级标签")
    @TableField("second_class")
    private String secondClass;

    @ApiModelProperty(value = "三级标签")
    @TableField("third_class")
    private String thirdClass;

    @ApiModelProperty(value = "四级标签")
    @TableField("four_class")
    private String fourClass;

    @ApiModelProperty(value = "数据量")
    @TableField("count")
    private Integer count;

    @ApiModelProperty(value = "比例")
    @TableField(exist = false)
    private Double ratio;

    @ApiModelProperty(value = "推荐度")
    @TableField(exist = false)
    private Double recommand;
}
