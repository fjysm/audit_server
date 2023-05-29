package com.swu.audit.org.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swu.audit.model.data.AuditText;
import com.swu.audit.model.data.ClassText;
import com.swu.audit.vo.model.checkCLass;
import com.swu.audit.model.data.LabelClass;
import com.swu.audit.org.mapper.ClassMapper;
import com.swu.audit.org.mapper.ClassTextMapper;
import com.swu.audit.org.service.AuditTextService;
import com.swu.audit.org.service.ClassTextService;
import com.swu.audit.vo.auditText.ClassResultVo;
import com.swu.audit.vo.auditText.checkClassResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class ClassTextServiceImpl extends ServiceImpl<ClassTextMapper, ClassText> implements ClassTextService {


    @Autowired
    private AuditTextService auditTextService;



    @Resource
    private ClassTextMapper classTextMapper;

    @Resource
    private ClassMapper classMapper;


    @Override
    public ClassResultVo getClassResult(Integer id){
        ClassResultVo classResultVo = new ClassResultVo();
        //查询审计文本的详细信息
        AuditText auditText = auditTextService.getById(id);
        classResultVo.setAuditText(auditText.getText());


        //根据id查询分类标签的信息
        QueryWrapper<ClassText> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("text_id",id).eq("type","system");
        List<ClassText> classList = classTextMapper.selectList(queryWrapper);
        List<LabelClass> labelList = new ArrayList<>();

        for (ClassText item:classList) {
            Integer classId = item.getClassId();
            QueryWrapper<LabelClass> query = new QueryWrapper<>();
            query.select("id", "first_class", "second_class","third_class","four_class")
                    .eq("id", classId);
            List<LabelClass> label = classMapper.selectList(query);
           LabelClass res = label.get(0);
           res.setRecommand(Double.valueOf(item.getRecommend()));
            labelList.add(label.get(0));
        }

        classResultVo.setClassResult(labelList);
        return classResultVo;

    }

    @Override
    public checkClassResultVo getCheckClassResult(Integer id){
        checkClassResultVo classResultVo = new checkClassResultVo();
        //查询审计文本的详细信息
        AuditText auditText = auditTextService.getById(id);
        classResultVo.setAuditText(auditText.getText());


        //根据id查询分类标签的信息
        QueryWrapper<ClassText> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("text_id",id);
        List<ClassText> classList = classTextMapper.selectList(queryWrapper);
        List<checkCLass> labelList = new ArrayList<>();

        for (ClassText item:classList) {
            Integer classId = item.getClassId();
            checkCLass checkcLass = new checkCLass();
            QueryWrapper<LabelClass> query = new QueryWrapper<>();
            query.select("id", "first_class", "second_class","third_class","four_class")
                    .eq("id", classId);
            List<LabelClass> label = classMapper.selectList(query);
            String type = item.getType();
            checkcLass.setFirstClass(label.get(0).getFirstClass());
            checkcLass.setId(label.get(0).getId());
           if(item.getRecommend() != null){
               checkcLass.setRecommand(Double.valueOf(item.getRecommend()));
           }
            checkcLass.setSecondClass(label.get(0).getSecondClass());
            checkcLass.setThirdClass(label.get(0).getThirdClass());
            checkcLass.setFourClass(label.get(0).getFourClass());
            checkcLass.setType(type.equals("system") ? "系统":"审计人员");
            labelList.add(checkcLass);
        }

        classResultVo.setClassResult(labelList);
        return classResultVo;

    }
}
