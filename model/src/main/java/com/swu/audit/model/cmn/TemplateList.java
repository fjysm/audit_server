package com.swu.audit.model.cmn;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.swu.audit.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "审计模板信息")
@TableName("template_list")
public class TemplateList extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板名称")
    @TableField("template_name")
    private String templateName;

    @ApiModelProperty(value = "模板编码")
    @TableField("template_code")
    private String templateCode;

    @ApiModelProperty(value = "类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "联系人姓名")
    @TableField("contacts_name")
    private String contactsName;

    @ApiModelProperty(value = "联系人手机")
    @TableField("contacts_phone")
    private String contactsPhone;

    @ApiModelProperty(value = "状态")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "版本")
    @TableField("version")
    private String version;
}
