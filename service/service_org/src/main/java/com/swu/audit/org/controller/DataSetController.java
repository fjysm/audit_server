package com.swu.audit.org.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swu.audit.common.result.Result;
import com.swu.audit.model.data.DataSet;
import com.swu.audit.model.org.OrgSet;
import com.swu.audit.org.service.DataSetService;
import com.swu.audit.vo.dataset.DataClassCount;
import com.swu.audit.vo.dataset.DataSetQueryVo;
import com.swu.audit.vo.org.OrgSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Api(tags = "审计文本数据集管理")
@RestController
@RequestMapping("/api/dataset")

public class DataSetController {
    @Autowired
    private DataSetService dataSetService;

    @ApiOperation("新增审计文本数据集")
    @PostMapping("createDataSet")
    public Result saveDataSet(@RequestBody DataSet dataSet){

        dataSet.setDataCount(0);
        dataSet.setClassifyCount(0);
        dataSet.setPendingClassifyCount(0);
        dataSet.setUnclassifyCount(0);
        //调用service
        boolean save = dataSetService.save(dataSet);
        if(save){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //分页条件查询数据集
    @ApiOperation("分页条件查询数据集")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageDataSet(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) DataSetQueryVo dataSetQueryVo){
        Page<DataSet> page = new Page<>(current,limit);

        QueryWrapper<DataSet> queryWrapper = new QueryWrapper<>();
        String datasetName = dataSetQueryVo.getDataSetName();
        if(!StringUtils.isEmpty(datasetName)){
            queryWrapper.like("name",datasetName);
        }


        Page<DataSet> DataSetPage = dataSetService.page(page,queryWrapper);
        return Result.ok(DataSetPage);

    }

    //删除数据集
    @ApiOperation("删除数据集")
    @DeleteMapping("{id}")
    public Result removeDataSet(@PathVariable Long id){
        boolean flag = dataSetService.removeById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    //获取单个数据集的信息
    @ApiOperation("获取单个数据集的信息")
    @GetMapping("{id}")
    public Result getDataSetInfo(@PathVariable Long id){
        DataSet dataSet = dataSetService.getById(id);
        if(dataSet.getType().equals("history")){
            dataSet.setType("历史数据");
        }else {
            dataSet.setType("新增数据");
        }
        return Result.ok(dataSet);
    }

    //下载新增数据样例
    @ApiOperation("下载新增数据样例")
    @GetMapping("/downloadnew")
    public ResponseEntity<Resource> downloadNewFile() throws IOException {

        //获取文件流
        Resource resource = new FileSystemResource("D:\\code\\audit-parent\\service\\service_org\\src\\main\\resources\\static\\example.xlsx");
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.xlsx");
        //创建响应实体
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    //下载新增数据样例
    @ApiOperation("下载历史数据样例")
    @GetMapping("/downloadhistory")
    public ResponseEntity<Resource> downloadHistoryFile() throws IOException {

        //获取文件流
        Resource resource = new FileSystemResource("D:\\code\\audit-parent\\service\\service_org\\src\\main\\resources\\static\\exampleHistory.xlsx");
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.xlsx");
        //创建响应实体
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    //下载新增数据样例
    @ApiOperation("获取数据集中待分类文本数量、已分类文本数量、未分类文本数量")
    @GetMapping("/getClasscount")
    public Result getClasscount() {
        Map<String, Integer> res = dataSetService.getSumOfThreeColumns();
        DataClassCount dataClassCount = new DataClassCount();

        dataClassCount.setUnClassifyCount(res.get("sum1"));
        dataClassCount.setPendingClassifyCount(res.get("sum2"));
        dataClassCount.setClassifyCount(res.get("sum3"));
        return Result.ok(dataClassCount);

    }

    //下载新增数据样例
    @ApiOperation("查询同类型数据集")
    @PostMapping("/getDataSet")
    public Result getDataSet(@RequestBody DataSetQueryVo dataSetQueryVo) {
        QueryWrapper<DataSet> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",dataSetQueryVo.getType());
        List<DataSet> dataSetList = dataSetService.list(queryWrapper);
        return Result.ok(dataSetList);

    }

}
