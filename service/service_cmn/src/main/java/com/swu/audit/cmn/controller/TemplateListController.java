package com.swu.audit.cmn.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swu.audit.cmn.service.TemplateService;
import com.swu.audit.common.result.Result;
import com.swu.audit.common.utils.MD5;
import com.swu.audit.model.cmn.TemplateList;
import com.swu.audit.model.org.OrgSet;
import com.swu.audit.vo.cmn.TemplateListQueryVo;
import com.swu.audit.vo.org.OrgSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

@Api(description = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/template")
@CrossOrigin(allowCredentials="true")
public class TemplateListController {

    @Autowired
    private TemplateService templateService;

    @ApiOperation("分页条件查询模板信息")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageTemplate(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TemplateListQueryVo templateListQueryVo){
        Page<TemplateList> page = new Page<>(current,limit);

        QueryWrapper<TemplateList> queryWrapper = new QueryWrapper<>();
        String template_name = templateListQueryVo.getTemplateName();
        String template_code = templateListQueryVo.getTemplateCode();
        if(!StringUtils.isEmpty(template_name)){
            queryWrapper.like("template_name",template_name);
        }
        if(!StringUtils.isEmpty(template_code)){
            queryWrapper.eq("template_code",template_code);
        }

        Page<TemplateList> templatePage = templateService.page(page,queryWrapper);
        return Result.ok(templatePage);

    }

    @ApiOperation("删除模板")
    @DeleteMapping("{id}")
    public Result removeOrgSet(@PathVariable Long id){
        boolean flag = templateService.removeById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("锁定模板")
    @PutMapping("lockTemplate/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询审计机构信息
        TemplateList templateList = templateService.getById(id);
        //设置状态
        templateList.setStatus(status);
        //调用方法
        templateService.updateById(templateList);
        return Result.ok();
    }

    @ApiOperation("批量删除模板")
    @DeleteMapping("batchRemove")
    public Result batchRemoveOrgSet(@RequestBody List<Long> idList) {
        templateService.removeByIds(idList);
        return Result.ok();
    }

    @ApiOperation("添加模板")
    @PostMapping("saveTemplate")
    public Result saveHospitalSet(@RequestBody TemplateList templateList){
        //设置状态 1 使用 0 不能使用
        templateList.setStatus(1);
        //调用service
        boolean save = templateService.save(templateList);
        if(save){
            return Result.ok();
        }else {
            return Result.fail();
        }

    }

    @ApiOperation("修改审计模板信息")
    @PostMapping("editTemplate")
    public Result editOrgSet(@RequestBody TemplateList templateList){
        boolean flag = templateService.updateById(templateList);
        if(flag){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }



}
