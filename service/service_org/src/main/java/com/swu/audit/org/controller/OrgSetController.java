package com.swu.audit.org.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swu.audit.common.utils.MD5;
import com.swu.audit.model.org.OrgSet;
import com.swu.audit.common.result.Result;
import com.swu.audit.org.service.OrgSetService;
import com.swu.audit.vo.org.OrgSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = "审计机构设置管理")
@RestController
@RequestMapping("/admin/org/orgSet")
@CrossOrigin(allowCredentials="true")
public class OrgSetController {

    //注入service
    @Autowired
    private OrgSetService orgSetService;

    //1.查找所有组织
    @ApiOperation("查找所有审计机构")
    @GetMapping("/findAll")
    public Result findAllOrg(){
        //调用service
        List<OrgSet> list = orgSetService.list();
        return Result.ok(list);
    }

    //2.删除组织
    @ApiOperation("删除审计机构")
    @DeleteMapping("{id}")
    public Result removeOrgSet(@PathVariable Long id){
        boolean flag = orgSetService.removeById(id);
       if(flag){
           return Result.ok();
       }else {
           return Result.fail();
       }
    }

    //3.分页条件查询审计机构
    @ApiOperation("分页条件查询审计机构")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageOrgSet(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false)OrgSetQueryVo orgSetQueryVo){
        Page<OrgSet> page = new Page<>(current,limit);

        QueryWrapper<OrgSet> queryWrapper = new QueryWrapper<>();
        String orgname = orgSetQueryVo.getOrgname();
        String orgcode = orgSetQueryVo.getOrgcode();
        if(!StringUtils.isEmpty(orgname)){
            queryWrapper.like("orgname",orgname);
        }
        if(!StringUtils.isEmpty(orgcode)){
            queryWrapper.eq("orgcode",orgcode);
        }

        Page<OrgSet> orgSetPage = orgSetService.page(page,queryWrapper);
        return Result.ok(orgSetPage);

    }

    //4.添加医院设置
    @ApiOperation("添加机构")
    @PostMapping("saveOrgSet")
    public Result saveHospitalSet(@RequestBody OrgSet orgSet){
        //设置状态 1 使用 0 不能使用
        orgSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        orgSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));

        //调用service
        boolean save = orgSetService.save(orgSet);
        if(save){
            return Result.ok();
        }else {
            return Result.fail();
        }

    }
    //5.根据id获取审计机构
    @ApiOperation("根据id获取审计机构")
    @GetMapping("getOrgSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        OrgSet orgSet = orgSetService.getById(id);
        return Result.ok(orgSet);
    }


    //6.修改医院设置
    @ApiOperation("修改审计机构设置")
    @PostMapping("editOrgSet")
    public Result editOrgSet(@RequestBody OrgSet orgSet){
        boolean flag = orgSetService.updateById(orgSet);
        if(flag){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //7.批量删除医院设置
    @ApiOperation("批量删除审计机构设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveOrgSet(@RequestBody List<Long> idList) {
        orgSetService.removeByIds(idList);
        return Result.ok();
    }

    //8 审计机构设置锁定和解锁
    @PutMapping("lockOrgSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        //根据id查询审计机构信息
        OrgSet orgSet = orgSetService.getById(id);
        //设置状态
        orgSet.setStatus(status);
        //调用方法
        orgSetService.updateById(orgSet);
        return Result.ok();
    }


}
