package com.boot.api.globals.utils;

import com.boot.api.globals.common.excel.ExcelRowDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Component
public class ExcelUtil {
    private static final String PATH = "";

    public <T> void excelDownload (List<T> vo, ExcelRowDto excelRowDto) throws IllegalAccessException, IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(excelRowDto.getSheetName());

        Row row;
        int rowNum = 1;
        int cellNum = 0;
        row = sheet.createRow(0);

        for(String rowName : excelRowDto.getRows()) {
            Cell cell = row.createCell(cellNum++);
            cell.setCellValue(rowName);
            cell.setCellStyle(cellStyle(0, workbook));
        }

        for(T data : vo) {
            row = sheet.createRow(rowNum++);
            cellNum = 0;
            for(Field field : data.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Cell cell = row.createCell(cellNum++);
                cell.setCellValue(field.get(data).toString());
                cell.setCellStyle(cellStyle(1, workbook));
            }
        }

        for(int i=0; i<sheet.getRow(0).getLastCellNum(); i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, Math.max(sheet.getColumnWidth(i), 3500));
        }

        try (FileOutputStream outputStream = new FileOutputStream(PATH + excelRowDto.getSheetName() + ".xlsx")) {
            workbook.write(outputStream);
        }

    }

    private CellStyle cellStyle(int index, XSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        if(index == 0) {
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        }
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        return cellStyle;
    }
}
