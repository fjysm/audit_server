package com.swu.audit.org.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swu.audit.common.excel.ExcelUtils;
import com.swu.audit.common.excel.HistoryData;
import com.swu.audit.common.excel.NewData;
import com.swu.audit.common.result.Result;
import com.swu.audit.model.User.UserInfo;
import com.swu.audit.model.data.*;
import com.swu.audit.org.service.*;
import com.swu.audit.vo.auditText.AuditClassResultVo;
import com.swu.audit.vo.auditText.AuditTextQueryVo;
import com.swu.audit.vo.auditText.ClassResultVo;
import com.swu.audit.vo.auditText.RecommandResult;
import com.swu.audit.vo.cart.Bar;
import com.swu.audit.vo.cart.Pie;
import com.swu.audit.vo.cart.StackArea;
import com.swu.audit.vo.label.ClassQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api(tags = "审计文本管理")
@RestController
@RequestMapping("/api/auditText")
public class AuditTextController {

    @Autowired
    private AuditTextService auditTextService;

    @Autowired
    private DataSetService dataSetService;

    @Autowired
    private ClassService classService;

    @Autowired
    private ClassTextService classTextService;

    @Autowired
    private ImportHistoryService importHistoryService;

    @Autowired
    private UserInfoService userInfoService;



    //上传EXCEL文件
    @ApiOperation("上传新增电力审计问题")
    @PostMapping("/uploadNewFile/{id}/{userId}/{quchong}")
    public Result uploadNewFile(@RequestParam("files") List<MultipartFile> files, @PathVariable int id,@PathVariable Integer userId,@PathVariable Integer quchong) {
        for (MultipartFile file : files) {
            //处理空文件
            if (file.isEmpty()) {
                continue;
            }
            if (!StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".xlsx")) {
                continue;
            }
            try(InputStream inputStream = file.getInputStream()) {
                List<NewData> result = ExcelUtils.readNewExcel(inputStream);
                List<AuditText> list = new ArrayList<>();
                for (NewData text:result) {
                    AuditText auditText =new AuditText();
                    auditText.setText(text.getQuestion());
                    auditText.setStatus("unClassify");
                    auditText.setCompany(text.getCompany());
                    auditText.setDatasetId(id);
                    list.add(auditText);
                }
                Integer count = 0;
                if(quchong == 0) {
                    auditTextService.saveBatch(list);
                   count = list.size();
                }else {
                    for (AuditText text: list) {
                        QueryWrapper<AuditText> query = new QueryWrapper<>();
                        query.eq("dataset_id",id).eq("text",text.getText());
                        if(auditTextService.list(query).size() == 0){
                            auditTextService.save(text);
                            count++;
                        }
                    }

                }
                DataSet dataSet = dataSetService.getById(id);
                dataSet.setDataCount(dataSet.getDataCount()+count);
                dataSet.setUnclassifyCount(dataSet.getUnclassifyCount()+count);
                dataSetService.updateById(dataSet);


                //更新导入记录表
                UserInfo userInfo = userInfoService.getById(userId);
                ImportHistory history = new ImportHistory();
                history.setImportType("文件上传");
                history.setDataCount(count);
                history.setDatasetId(id);
                history.setUserName(userInfo.getUserName());
                importHistoryService.save(history);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.ok("文件上传成功");
    }
    //上传EXCEL文件
    @ApiOperation("上传历史电力审计问题")
    @PostMapping("/uploadHistoryFile/{id}/{userId}/{quchong}")
    public Result uploadHistoryFile(@RequestParam("files") List<MultipartFile> files, @PathVariable int id,@PathVariable Integer userId,@PathVariable Integer quchong) {
        for (MultipartFile file : files) {
            //处理空文件
            if (file.isEmpty()) {
                continue;
            }
            if (!StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), ".xlsx")) {
                continue;
            }
            try(InputStream inputStream = file.getInputStream()) {
                List<HistoryData> result = ExcelUtils.readHistoryExcel(inputStream);
                List<AuditText> list = new ArrayList<>();
                //批量插入
                for (HistoryData text:result) {
                    AuditText auditText =new AuditText();
                    auditText.setText(text.getQuestion()+" "+text.getQuestion_range()+" " + text.getQuestion_class() + " " + text.getQuestion_qualitative());
                    auditText.setStatus("unClassify");
                    auditText.setDatasetId(id);
                    auditText.setCompany(text.getCompany());
                    list.add(auditText);
                }
                Integer count = 0;
                if(quchong == 0) {
                    auditTextService.saveBatch(list);
                    count = list.size();
                }else {
                    for (AuditText text: list) {
                        QueryWrapper<AuditText> query = new QueryWrapper<>();
                        query.eq("dataset_id",id).eq("text",text.getText());
                        if(auditTextService.list(query).size() == 0){
                            auditTextService.save(text);
                            count++;
                        }
                    }

                }
                DataSet dataSet = dataSetService.getById(id);
                dataSet.setDataCount(dataSet.getDataCount()+count);
                dataSet.setUnclassifyCount(dataSet.getUnclassifyCount()+count);
                dataSetService.updateById(dataSet);


                //更新导入记录表
                UserInfo userInfo = userInfoService.getById(userId);
                ImportHistory history = new ImportHistory();
                history.setImportType("文件上传");
                history.setDataCount(count);
                history.setDatasetId(id);
                history.setUserName(userInfo.getUserName());
                importHistoryService.save(history);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.ok("文件上传成功");
    }
    @ApiOperation("根据数据集id获取未分类电力审计文本")
    @PostMapping("getUnClassifyText/{id}/{current}/{limit}")
    public Result getUnClassifyText(@PathVariable Long id,@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) AuditTextQueryVo auditTextQueryVo){
        Page<AuditText> page = new Page<>(current,limit);
        QueryWrapper<AuditText> queryWrapper = new QueryWrapper<>();
        if(auditTextQueryVo != null){
            String keyWords = auditTextQueryVo.getKeyWord();
            queryWrapper.like("text",keyWords)
                    .eq("dataset_id",id)
                    .eq("status","unClassify");
        }else {
            queryWrapper.eq("dataset_id",id)
                    .eq("status","unClassify");
        }

        Page<AuditText> labelClassPage = auditTextService.page(page,queryWrapper);
        return Result.ok(labelClassPage);
    }

    @ApiOperation("根据数据集id获取待分类电力审计文本")
    @PostMapping("getPendingClassifyText/{id}/{current}/{limit}")
    public Result getPendingClassifyText(@PathVariable Long id,@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) AuditTextQueryVo auditTextQueryVo){
        Page<AuditText> page = new Page<>(current,limit);
        QueryWrapper<AuditText> queryWrapper = new QueryWrapper<>();
        if(auditTextQueryVo != null){
            String keyWords = auditTextQueryVo.getKeyWord();
            queryWrapper.like("text",keyWords)
                    .eq("dataset_id",id)
                    .eq("status","pendingClassify");
        }else {
            queryWrapper.eq("dataset_id",id)
                    .eq("status","pendingClassify");
        }

        Page<AuditText> labelClassPage = auditTextService.page(page,queryWrapper);
        return Result.ok(labelClassPage);
    }

    @ApiOperation("根据数据集id获取已分类电力审计文本")
    @PostMapping("getClassifyText/{id}/{current}/{limit}")
    public Result getClassifyText(@PathVariable Integer id,@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) AuditTextQueryVo auditTextQueryVo){
        Page<AuditClassResultVo> page = new Page<>(current,limit);
        String keyWords = "";
        if(auditTextQueryVo != null){
            keyWords = auditTextQueryVo.getKeyWord();

        }
        List<AuditClassResultVo> classResultVos = auditTextService.selectAuditClassList(id, page,keyWords);
        page.setRecords(classResultVos);
        return Result.ok(page);

    }

    @ApiOperation("单条审计文本分类")
    @PostMapping("classify/{id}/{datasetId}")
    public Result classify(@PathVariable Integer id, @PathVariable Integer datasetId, @RequestBody RecommandResult result){
        DataSet dataSet = dataSetService.getById(datasetId);
        //查询分类标签id,向待分类表中插入数据
        for (String text:result.getClassList()) {
           String[] classList = text.split("-");
            QueryWrapper<LabelClass> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("first_class",classList[0])
                    .eq("second_class",classList[1])
                    .eq("third_class",classList[2])
                    .eq("four_class",classList[3]);
           List<LabelClass> res = classService.list(queryWrapper);
           String score;
           if(dataSet.getType().equals("new")){
               score = classList[4].substring(2,4) + "." + classList[4].substring(4,5);
           }else {
               score =classList[4];
           }

            ClassText classText = new ClassText();
            classText.setType("system");
            classText.setRecommend(score);
            classText.setClassId(res.get(0).getId());
            classText.setTextId(id);
            classTextService.save(classText);
        }

        //更新文本的状态为待分类
        UpdateWrapper<AuditText> updateWrapper = Wrappers.update();
        updateWrapper.set("status", "pendingClassify")
                .eq("id", id);
        auditTextService.update(updateWrapper);

        //更新待分类文本数量
        UpdateWrapper<DataSet> updateDataSetWrapper = Wrappers.update();

        dataSet.setPendingClassifyCount(dataSet.getPendingClassifyCount() + 1);
        dataSet.setUnclassifyCount(dataSet.getUnclassifyCount() -1);
       dataSetService.updateById(dataSet);

       return Result.ok();

    }

    @ApiOperation("确认最后分类标签")
    @GetMapping("confirmLabel/{text_id}/{class_id}")
    public Result confirmLabel(@PathVariable Integer text_id,@PathVariable Integer class_id){
        UpdateWrapper<AuditText> updateWrapper = Wrappers.update();
        updateWrapper.set("status", "classify")
                .set("class_id", class_id)
                .eq("id", text_id);
        auditTextService.update(updateWrapper);


        //更新审计文本信息
        AuditText auditText = auditTextService.getById(text_id);
        auditText.setConfirmType("confirm");
        auditTextService.updateById(auditText);
        //更新数据集信息
        DataSet dataSet = dataSetService.getById(auditText.getDatasetId());
        dataSet.setClassifyCount(dataSet.getClassifyCount()+1);
        if (dataSet.getPendingClassifyCount()!= 0){
            dataSet.setPendingClassifyCount(dataSet.getPendingClassifyCount()-1);
        }
        dataSetService.updateById(dataSet);

        //更新标签信息
        LabelClass labelClass = classService.getById(class_id);
        labelClass.setCount(labelClass.getCount()+1);
        classService.updateById(labelClass);

        return Result.ok();

    }

    @ApiOperation("审核确认最后分类标签")
    @GetMapping("confirmCheckLabel/{text_id}/{class_id}")
    public Result confirmCheckLabel(@PathVariable Integer text_id,@PathVariable Integer class_id){
        UpdateWrapper<AuditText> updateWrapper = Wrappers.update();
        updateWrapper.set("status", "classify")
                .set("class_id", class_id)
                .eq("id", text_id);
        auditTextService.update(updateWrapper);

        //更新审计文本信息
        AuditText auditText = auditTextService.getById(text_id);
        auditText.setConfirmType("check");
        auditTextService.updateById(auditText);
        //更新数据集信息
        DataSet dataSet = dataSetService.getById(auditText.getDatasetId());
        dataSet.setClassifyCount(dataSet.getClassifyCount()+1);
        if (dataSet.getPendingClassifyCount()!= 0){
            dataSet.setPendingClassifyCount(dataSet.getPendingClassifyCount()-1);
        }

        dataSetService.updateById(dataSet);

        //更新标签信息
        LabelClass labelClass = classService.getById(class_id);
        labelClass.setCount(labelClass.getCount()+1);
        classService.updateById(labelClass);

        return Result.ok();



    }

    @ApiOperation("查看某个标签下的全部审计文本")
    @GetMapping("getAllLabelAuditText/{class_id}/{current}/{limit}")
    public Result getAllLabelAuditText(@PathVariable Integer class_id,@PathVariable long current, @PathVariable long limit){
        Page<AuditText> page = new Page<>(current,limit);
        QueryWrapper<AuditText> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("class_id",class_id)
                .eq("status","classify");
        Page<AuditText> AuditTextPage = auditTextService.page(page,queryWrapper);
        return Result.ok(AuditTextPage);

    }
    @ApiOperation("查看所有待审核审计文本")
    @PostMapping("getCheckAuditText/{current}/{limit}")
    public Result getCheckAuditText(@PathVariable long current, @PathVariable long limit,@RequestBody(required = false) AuditTextQueryVo auditTextQueryVo){
        Page<AuditText> page = new Page<>(current,limit);
        QueryWrapper<AuditText> queryWrapper = new QueryWrapper<>();
        if(auditTextQueryVo != null){
            String keyWords = auditTextQueryVo.getKeyWord();
            queryWrapper.like("text",keyWords)
                    .eq("status","check");
        }else {
            queryWrapper.eq("status","check");
        }

        Page<AuditText> labelClassPage = auditTextService.page(page,queryWrapper);
        return Result.ok(labelClassPage);

    }

    @ApiOperation("获取近7天上传的文本数量")
    @GetMapping("getDataCountByDay")
    public Result getDataCountByDay(){
        List<Map<String, Object>> result = auditTextService.getDataCountByDay();
        List<Bar> res = new ArrayList<>();
        for (Map<String, Object> item : result) {
            Bar data = new Bar();
            Integer d = (Integer) item.get("count");
            Date date = (Date) item.get("day");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(date);
           data.setDate(dateString);
           data.setCount(d);
           res.add(data);

        }
        return Result.ok(res);
    }

    @ApiOperation("获取近7天系统分类数量和审计人员确认数量")
    @GetMapping("countByDayAndClassifyType")
    public Result countByDayAndClassifyType(){
        List<Map<String, Object>> result = auditTextService.countByDayAndClassifyType();
        List<StackArea> res = new ArrayList<>();
        for (Map<String, Object> item : result) {
            StackArea data = new StackArea();
            Integer d = (Integer) item.get("count");
            String type = (String) item.get("confirm_type");
            Date date = (Date) item.get("day");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(date);
            data.setDate(dateString);
            data.setCount(d);
            data.setType(type);
            if(type != null){
                res.add(data);
            }

        }
        return Result.ok(res);
    }

    @ApiOperation("获取准确率")
    @GetMapping("getAccurate")
    public Result getAccurate(){
        List<Map<String, Object>> result = auditTextService.getAccurate();
        Integer checkCount = 0;
        Integer confirmCount = 0;
        for (Map<String, Object> item : result) {

            Integer d = (Integer) item.get("count");
            String type = (String) item.get("confirm_type");
            if(type != null && type.equals("confirm")){
                confirmCount += d;
            }else {
                checkCount += d;
            }


        }
        double res = (double)checkCount /(checkCount + checkCount);
        return Result.ok(String.format("%.2f", res));
    }

    @ApiOperation("获取各公司文本数量")
    @GetMapping("getCompany")
    public Result getCompany(){
        List<Map<String, Object>> result = auditTextService.getCompany();
        List<Pie> res = new ArrayList<>();
        for (Map<String, Object> item : result) {
            Pie data = new Pie();
            Integer d = (Integer) item.get("count");
            String company = (String) item.get("company");
            if(company != null){

                data.setCompany(company);
                data.setCount(d);
                res.add(data);
            }

        }
        return Result.ok(res);
    }

    @ApiOperation("合并数据集")
    @GetMapping("mergeDataSet/{id}/{insetId}/{quchong}/{userId}")
    public Result mergeDataSet(@PathVariable Integer id,@PathVariable Integer insetId,@PathVariable Integer quchong,@PathVariable Integer userId){
        QueryWrapper<AuditText> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dataset_id",id);
        List<AuditText> auditTextList = auditTextService.list(queryWrapper);
        Integer count = 0;
        for (AuditText text: auditTextList ) {
            //去重
            if(quchong.equals(1)){
                QueryWrapper<AuditText> query = new QueryWrapper<>();
                query.eq("dataset_id",insetId).eq("text",text.getText());
                List<AuditText> auditTextList1 = auditTextService.list(query);
                if(auditTextService.list(query).size() == 0){
                    text.setDatasetId(insetId);
                    auditTextService.save(text);
                    count++;
                }
            }else {
                //不去重
                text.setDatasetId(insetId);
                auditTextService.save(text);
                count++;
            }
        }
        UserInfo userInfo = userInfoService.getById(userId);
        ImportHistory history = new ImportHistory();
        history.setImportType("数据集合并");
        history.setDataCount(count);
        history.setDatasetId(insetId);
        history.setUserName(userInfo.getUserName());
        importHistoryService.save(history);
        return Result.ok();
    }



}
