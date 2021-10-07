package com.cnstar.interfacetestmock.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
public class ExcelUtil {
    private XSSFWorkbook workbook;

    public ExcelUtil(String path) {
        File fl = new File(path);
        try {
            InputStream inputStream = fl.isAbsolute() ? new FileInputStream(fl) : Thread.currentThread().getContextClassLoader().getResource(path).openStream();
            workbook = new XSSFWorkbook(inputStream);
        } catch (Exception e) {
            log.error("Fail to open excel file");
        }
    }

    public String[][] getDataWithoutHeadAndFirstCol(String sheetName) {
        return this.getDataArea(sheetName, 2, 2);
    }

    public String[][] getDataWithoutHeader(String sheetName) {
        return this.getDataArea(sheetName, 2, 1);
    }

    public String[][] getDataWithHeader(String sheetName) {
        return this.getDataArea(sheetName, 1, 1);
    }

    private String[][] getDataArea(String sheetName, int rowStartIndex, int colStartIndex) {
        String[][] dataSets = null;
        try {
            XSSFSheet sheet = this.workbook.getSheet(sheetName);
            int totalRows = sheet.getLastRowNum() + 1;
            int totalCols = sheet.getRow(0).getLastCellNum();
            if (rowStartIndex > totalRows || colStartIndex > totalCols) {
                return null;
            }
            dataSets = new String[totalRows - rowStartIndex + 1][totalCols - colStartIndex + 1];
            for (int i = rowStartIndex - 1; i < totalRows; i++) {
                XSSFRow rows = sheet.getRow(i);
                for (int j = colStartIndex - 1; j < totalCols; j++) {
                    XSSFCell cell = rows.getCell(j);
                    dataSets[i - rowStartIndex + 1][j - colStartIndex + 1] = getCellValue(cell);
                }
            }
        } catch (Exception e) {
            log.info("Error reading file", e);
        }
        return dataSets;
    }

    private String getCellValue(XSSFCell cell) {
        if (null == cell) {
            return "";
        } else {
            String cellValue;
            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    cellValue = numericParse(cell);
                    break;
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case FORMULA:
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                case BLANK:
                case ERROR:
                    cellValue = "";
                    break;
                default:
                    cellValue = cell.toString();
            }
            return cellValue;
        }
    }

    private String numericParse(XSSFCell cell) {
        String cellValue;
        if (DateUtil.isCellDateFormatted(cell)) {
            cellValue = new DataFormatter().formatCellValue(cell);
        } else {
            DataFormatter fmt = new DataFormatter();
            cellValue = fmt.formatCellValue(cell);
        }
        return cellValue;
    }
}
