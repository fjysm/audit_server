package com.swu.audit.org.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swu.audit.common.result.Result;
import com.swu.audit.model.data.DataSet;
import com.swu.audit.model.data.LabelClass;
import com.swu.audit.org.mapper.ClassMapper;
import com.swu.audit.org.service.ClassService;
import com.swu.audit.vo.dataset.DataSetQueryVo;
import com.swu.audit.vo.label.ClassQueryVo;
import com.swu.audit.vo.label.DeleteClassVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Api(tags = "标签数据集管理")
@RestController
@RequestMapping("/api/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Resource
    private ClassMapper classMapper;

    //分页条件查询数据集
    @ApiOperation("分页条件查询标签")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageClass(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) ClassQueryVo classQueryVo){
        Page<LabelClass> page = new Page<>(current,limit);
        QueryWrapper<LabelClass> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","first_class","second_class","third_class","four_class","count/(select sum(count) from class) as ratio");
        String keyWords = classQueryVo.getClassKeyWords();
        if(!StringUtils.isEmpty(keyWords)){
            queryWrapper.like("first_class",keyWords)
                    .or()
                    .like("second_class",keyWords)
                    .or()
                    .like("third_class",keyWords)
                    .or()
                    .like("four_class",keyWords);
        }
        Page<LabelClass> labelClassPage = classService.page(page,queryWrapper);
        return Result.ok(labelClassPage);
    }

    //创建标签
    @ApiOperation("创建标签")
    @PostMapping("createClass")
    public Result createClass(@RequestBody LabelClass labelClass){
        labelClass.setCount(0);
        //调用service
        boolean save = classService.save(labelClass);
        if(save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }
    //编辑标签
    @ApiOperation("编辑标签")
    @PostMapping("editClass")
    public Result editClass(@RequestBody LabelClass labelClass){

        //调用service
        boolean save = classService.updateById(labelClass);
        if(save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }


    @ApiOperation("删除标签")
    @PostMapping("deleteClass/{id}")
    public Result deleteClass(@PathVariable int id){

        //调用service
        boolean delete = classService.removeById(id);
        if(delete){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("批量删除标签")
    @PostMapping("deleteBatchClass")
    public Result deleteBatchClass(@RequestBody List<Integer> ids){
        boolean delete = true;
        for (Integer id:ids) {
             delete = classService.removeById(id);
            if(!delete)
                break;
        }
        if(delete){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @ApiOperation("查询所有标签")
    @GetMapping("/getAllClass")
    public Result getAllClass(){
        List<LabelClass> labelClassList = classService.list();
       return Result.ok(labelClassList);
    }

}
