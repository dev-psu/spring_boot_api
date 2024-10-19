package com.boot.api.globals.common.excel;

import lombok.Data;

import java.util.List;

@Data
public class ExcelRowDto {
    private String sheetName;
    private List<String> rows;

    public ExcelRowDto(String sheetName, List<String> rows) {
        this.sheetName = sheetName;
        this.rows = rows;
    }
}
