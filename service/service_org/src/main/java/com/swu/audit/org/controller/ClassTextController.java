package com.swu.audit.org.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.swu.audit.common.result.Result;
import com.swu.audit.model.data.AuditText;
import com.swu.audit.model.data.ClassText;
import com.swu.audit.model.data.DataSet;
import com.swu.audit.org.service.AuditTextService;
import com.swu.audit.org.service.ClassTextService;
import com.swu.audit.org.service.DataSetService;
import com.swu.audit.vo.auditText.ClassResultVo;
import com.swu.audit.vo.auditText.checkClassResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;

@Api(tags = "待分类审计文本和标签管理")
@RestController
@RequestMapping("/api/classText")
public class ClassTextController {

    @Autowired
    private ClassTextService classTextService;
    @Autowired
    private AuditTextService auditTextService;



    @Autowired
    private  DataSetService dataSetService;
    //获取单个数据集的信息
    @ApiOperation("获取系统为审计文本推荐的分类标签")
    @GetMapping("{id}")
    public Result getLabel(@PathVariable Integer id){
        ClassResultVo classResultVo = classTextService.getClassResult(id);
        return Result.ok(classResultVo);
    }

    @ApiOperation("获取系统为审计文本推荐的分类标签")
    @GetMapping("getCheckLabel/{id}")
    public Result getCheckLabel(@PathVariable Integer id){
        checkClassResultVo classResultVo = classTextService.getCheckClassResult(id);
        return Result.ok(classResultVo);
    }

    //审计人员反馈标签
    @ApiOperation("审计人员反馈标签")
    @GetMapping("editClass/{text_id}/{class_id}")
    public Result editClass(@PathVariable Integer text_id,@PathVariable Integer class_id){
        ClassText classText = new ClassText();
        classText.setClassId(class_id);
        classText.setTextId(text_id);
        classText.setType("user");
        boolean res = classTextService.save(classText);
        UpdateWrapper<AuditText> updateWrapper = Wrappers.update();
        updateWrapper.set("status", "check")
                .eq("id", text_id);
        auditTextService.update(updateWrapper);

        //更新数据集信息
        AuditText auditText =auditTextService.getById(text_id);
        DataSet dataSet = dataSetService.getById(auditText.getDatasetId());
        dataSet.setPendingClassifyCount(dataSet.getPendingClassifyCount()-1);
        dataSetService.updateById(dataSet);
        if(res){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //管理员手动审核
    @ApiOperation("管理员手动审核")
    @GetMapping("confirmClass/{text_id}/{class_id}")
    public Result confirmClass(@PathVariable Integer text_id,@PathVariable Integer class_id){
        UpdateWrapper<AuditText> updateWrapper = Wrappers.update();
        updateWrapper.set("status", "classify")
                .set("class_id",class_id)
                .eq("id", text_id);
        boolean res = auditTextService.update(updateWrapper);

        //更新数据集信息
        AuditText auditText = auditTextService.getById(text_id);
        DataSet dataSet = dataSetService.getById(auditText.getDatasetId());
        dataSet.setClassifyCount(dataSet.getClassifyCount()+1);
        dataSetService.updateById(dataSet);
        if(res){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }


}
