package com.swu.audit.org.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swu.audit.common.result.Result;
import com.swu.audit.model.data.ImportHistory;
import com.swu.audit.model.data.LabelClass;
import com.swu.audit.org.service.ClassService;
import com.swu.audit.org.service.ImportHistoryService;
import com.swu.audit.vo.label.ClassQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Api(tags = "导入历史管理")
@RestController
@RequestMapping("/api/importHistory")
public class ImportHistoryController {

    @Autowired
    private ImportHistoryService importHistoryService;

    @ApiOperation("分页条件查导入记录")
    @GetMapping("findPage/{id}")
    public Result findPageClass(@PathVariable Integer id){
        Page<ImportHistory> page = new Page<>(1,10);
        QueryWrapper<ImportHistory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dataset_id",id).orderByDesc("create_time");
        Page<ImportHistory> importHistoryPage = importHistoryService.page(page,queryWrapper);
        return Result.ok(importHistoryPage);
    }
}
