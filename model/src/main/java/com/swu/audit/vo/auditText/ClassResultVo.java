package com.swu.audit.vo.auditText;

import com.swu.audit.model.data.LabelClass;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "系统推荐分类结果返回")
public class ClassResultVo {
    @ApiModelProperty(value = "审计文本")
    private String auditText;

    @ApiModelProperty(value = "系统推荐的分类结果")
    private List<LabelClass> classResult;

}
