package com.swu.audit.org.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swu.audit.model.data.AuditText;
import com.swu.audit.org.mapper.AuditTextMapper;
import com.swu.audit.org.service.AuditTextService;
import com.swu.audit.vo.auditText.AuditClassResultVo;
import com.swu.audit.vo.auditText.ClassResultVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuditTextServiceImpl extends ServiceImpl<AuditTextMapper, AuditText> implements AuditTextService {
    @Resource private AuditTextMapper auditTextMapper;

    @Override
    public List<String> classify(String text){
        List<String> result = new ArrayList<>();


        Thread pythonThread = new Thread() {
            public void run() {
                ProcessBuilder pb = new ProcessBuilder("python", "D:\\毕业论文算法\\服务器部署\\new.py", text);
                Process p = null;
                try {
                    p = pb.start();
                    BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    int exitCode = p.waitFor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // ... Process the results ...
            }
        };
        pythonThread.start();


        while (pythonThread.isAlive()) {
            // ... Update the progress bar ...
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public List<AuditClassResultVo> selectAuditClassList(Integer id, IPage<AuditClassResultVo> page,String keyword){
        return auditTextMapper.selectAuditClassList(page, id, "classify",keyword);
    }

    @Override
    public List<Map<String, Object>> getDataCountByDay() {
        return auditTextMapper.countDataByDay();
    }

    @Override
    public List<Map<String, Object>> countByDayAndClassifyType(){
        return auditTextMapper.countByDayAndClassifyType();
    }
    @Override
    public List<Map<String, Object>> getAccurate(){
        return auditTextMapper.getAccurate();
    }

    @Override
    public List<Map<String, Object>> getCompany(){
        return auditTextMapper.getCompany();
    }
}
