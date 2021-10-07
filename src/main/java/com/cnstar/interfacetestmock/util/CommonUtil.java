package com.cnstar.interfacetestmock.util;

import com.cnstar.interfacetestmock.constant.Constants;
import com.cnstar.interfacetestmock.enumdb.BaseDbEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {
    public static <T extends BaseDbEnum> List<Map<String, Object>> getTestData(String filePath, String sheetName, Class<T> clazz) {
        ExcelUtil excelReader = new ExcelUtil(filePath);
        String[][] data = excelReader.getDataWithHeader(filePath);
        List<Map<String, Object>> all = new ArrayList<>();
        for (int i =1; i < data.length; i++) {
            Map<String, Object> oneRow = new HashMap<>(11);
            for (int k = 0; k < data[i].length; k++) {
                T[] mm = clazz.getEnumConstants();
                for (T t: mm) {
                    if (t.getName().equalsIgnoreCase(data[0][k].trim())) {
                        oneRow.put(t.getName(),CommonUtil.refactorData(data[i][k]));
                    }
                }
            }
            all.add(oneRow);
        }
        return all;
    }

    private static Object refactorData(String input) {
        input = input.trim();
        if (input.equals(Constants.SLASH)) {
            return "";
        } else if (input.equals(Constants.SLASH_DOUBLE)) {
            return null;
        } else if (input.startsWith(Constants.TAG_DATE)) {
            int days = Integer.parseInt(input.substring(input.indexOf(Constants.COLON) + 1));
            return DateUtil.getDateBefore(Constants.DATE_DAY_FORMAT, days);
        } else if (input.startsWith(Constants.DATE1)) {
            return refactorData(input.substring(input.indexOf(Constants.COLON) + 1));
        } else if (input.startsWith(Constants.TAG_DATE_RANGE)) {
            String[] strArray = input.substring(input.indexOf(Constants.COLON) + 1).split(Constants.COMMA);
            return DateUtil.getRandomRangeDate(strArray[0], strArray[1]);
        }
        return input.trim();
    }
}
