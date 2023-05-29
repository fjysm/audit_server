package com.swu.audit.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class HistoryData {
    @ExcelProperty(index = 0)
    private String question;

    @ExcelProperty(index = 1)
    private String question_range;

    @ExcelProperty(index = 2)
    private String question_class;

    @ExcelProperty(index = 3)
    private String question_qualitative;

    @ExcelProperty(index = 4)
    private String company;
}
