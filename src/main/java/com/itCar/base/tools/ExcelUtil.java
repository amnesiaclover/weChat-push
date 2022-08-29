package com.itCar.base.tools;

import com.alibaba.fastjson.JSONObject;
import com.itCar.base.api.quartz.entity.QuartzEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ExcelUtil 
 * @Description: TODO
 * @author: liuzg
 * @Date: 2022/8/11 9:15
 * @Week: 星期四
 * @Version: v1.0
 */
@Slf4j
public class ExcelUtil extends cn.hutool.poi.excel.ExcelUtil {

    public static Workbook exportDBData(Workbook workbook, List<Map<String, Object>> data) {
        if (null == workbook) {
            return null;
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row headRow = sheet.getRow(0);
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Map<String, Object> rowData = data.get(i);
            int cellIndex = 0;
            for (Cell cell : headRow) {
                Cell ce = row.createCell(cellIndex);
                String headerKey = cell.getStringCellValue();
                if (null == rowData || !rowData.containsKey(headerKey) || null == rowData.get(headerKey)) {
                    ce.setCellValue("");
                } else {
                    ce.setCellValue(String.valueOf(rowData.get(cell.getStringCellValue())));
                }
                cellIndex++;
            }
        }
        return workbook;
    }

    public static Workbook exportDBData(Workbook workbook, List<JSONObject> data, Integer headerline) {
        if (null == workbook) {
            return null;
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row headRow = sheet.getRow(headerline);
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Map<String, Object> rowData = data.get(i);
            int cellIndex = 0;
            for (Cell cell : headRow) {
                Cell ce = row.createCell(cellIndex);
                String headerKey = cell.getStringCellValue();
                if (null == rowData || !rowData.containsKey(headerKey) || null == rowData.get(headerKey)) {
                    ce.setCellValue("0");
                } else {
                    ce.setCellValue(String.valueOf(rowData.get(cell.getStringCellValue())));
                }
                cellIndex++;
            }
        }
        return workbook;
    }


    public static void copyRow(Row createRow, Row copyRow) {
        createRow.setHeight(copyRow.getHeight());
        for (int i = 0; i < copyRow.getLastCellNum(); i++) {
            createRow.createCell(i).setCellValue(copyRow.getCell(i).getStringCellValue());
            createRow.getCell(i).setCellStyle(copyRow.getCell(i).getCellStyle());
        }
    }


    public static void copyRow(Sheet sheet, int baseRowNum, int copyLength) {
        Row baseRow = sheet.getRow(baseRowNum);
        for (int i = 0; i < copyLength - 1; i++) {
            Row row = sheet.createRow(i + baseRowNum + 1);
            copyRow(row, baseRow);
        }
    }


    public static void copyRow(Sheet sheet, int baseRowNum, int shiftRowNum, int copyLength) {
        Row baseRow = sheet.getRow(baseRowNum);
        for (int i = 0; i < copyLength - 1; i++) {
            sheet.shiftRows(i + shiftRowNum, sheet.getLastRowNum(), 1);
            Row row = sheet.createRow(i + shiftRowNum);
            copyRow(row, baseRow);
        }
    }


    public static void replace(Row row, Map<String, Object> params) {
        for (Cell cell : row) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String cellValue = StringUtil.replaceSpecialCharacters(cell.getStringCellValue());
                if (cellValue.equals(entry.getKey())) {
                    cell.setCellValue(null == entry.getValue() ? "" : entry.getValue().toString());
                }
            }
        }
    }

    public static void replace(Sheet sheet, List<Map<String, Object>> params, int startRow) {
        for (int i = 0; i < params.size(); i++) {
            Map<String, Object> rowData = params.get(i);
            Row row = sheet.getRow(i + startRow);
            replace(row, rowData);
        }
    }

    // 与上接收参数不同
    public static void replacel(Sheet sheet, List<QuartzEntity> params, int startRow) {
        for (int i = 0; i < params.size(); i++) {
            Map<String, Object> rowData = (Map<String, Object>) params.get(i);
            Row row = sheet.getRow(i + startRow);
            replace(row, rowData);
        }
    }


    public static void replace(Sheet sheet, Map<String, Object> params, int startRow) {
        for (int i = startRow; i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(startRow);
            replace(row, params);
        }
    }


    public static void replace(Sheet sheet, Map<String, Object> params, int startRow, int endRow) {
        for (int i = startRow; i < endRow; i++) {
            Row row = sheet.getRow(startRow);
            replace(row, params);
        }
    }
}
