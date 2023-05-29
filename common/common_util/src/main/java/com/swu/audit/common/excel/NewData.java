package com.swu.audit.common.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class NewData {

    @ExcelProperty(index = 0)
    private String question;

    @ExcelProperty(index = 1)
    private String company;
}
