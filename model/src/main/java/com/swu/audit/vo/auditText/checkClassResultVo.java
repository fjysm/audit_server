package com.swu.audit.vo.auditText;

import com.swu.audit.model.data.LabelClass;
import com.swu.audit.vo.model.checkCLass;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "待审核分类结果返回")
public class checkClassResultVo {
    @ApiModelProperty(value = "审计文本")
    private String auditText;

    @ApiModelProperty(value = "系统推荐的分类结果")
    private List<checkCLass> classResult;
}
