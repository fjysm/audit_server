package com.swu.audit.common.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {
    public static List<HistoryData> readHistoryExcel(InputStream inputStream) throws IOException {
        List<HistoryData> result = new ArrayList<>();
        EasyExcel.read(inputStream, HistoryData.class, new AnalysisEventListener<HistoryData>() {
            @Override
            public void invoke(HistoryData rowData, AnalysisContext context) {
                result.add(rowData);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 解析完成后的操作
            }
        }).sheet().doRead();
        return result;
    }

    public static List<NewData> readNewExcel(InputStream inputStream) throws IOException {
        List<NewData> result = new ArrayList<>();
        EasyExcel.read(inputStream, NewData.class, new AnalysisEventListener<NewData>() {
            @Override
            public void invoke(NewData rowData, AnalysisContext context) {
                result.add(rowData);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 解析完成后的操作
            }
        }).sheet().doRead();
        return result;
    }
}
