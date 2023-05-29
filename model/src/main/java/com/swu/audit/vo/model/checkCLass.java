package com.swu.audit.vo.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.swu.audit.model.data.LabelClass;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "待审核分类标签")
public class checkCLass extends LabelClass {

    @ApiModelProperty(value = "类型")
    private String type;
}
