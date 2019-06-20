package com.minliu.demo.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel工具类
 * ClassName: ExcelUtils <br>
 * date: 2:33 PM 12/03/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    private static final String XLS_TYPE = "xls";

    private static final String XLSX_TYPE = "xlsx";

    private ExcelUtils() {
        //
    }

    public static Workbook getWorkBook(File file) {
        if (file != null) {
            try {
                String fileName = file.getName();
                if (fileName.endsWith(XLS_TYPE)) {
                    return new HSSFWorkbook(new FileInputStream(file));
                } else if (fileName.endsWith(XLSX_TYPE)) {
                    return new XSSFWorkbook(new FileInputStream(file));
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static String getCellValue(Cell cell) {
        if (cell != null) {
            CellType type = cell.getCellType();
            switch (type) {
                case _NONE:
                    return "";
                case STRING:
                    return cell.getStringCellValue();
                case BOOLEAN:
                    return cell.getBooleanCellValue() + "";
                case NUMERIC:
                    return cell.getNumericCellValue() + "";
                case ERROR:
                    return cell.getErrorCellValue() + "";
                default:
                    return "";
            }
        }
        return null;
    }

    /**
     * 获取工作簿第一个sheet页的表头
     *
     * @param workbook 工作簿
     * @return List
     */
    public static List<String> getHeaders(Workbook workbook) {
        Preconditions.checkNotNull(workbook);
        List<String> resultList = Lists.newArrayList();
        try {
            Sheet sheet = workbook.getSheetAt(0);
            Preconditions.checkNotNull(sheet, "sheet页不能为空");
            Row firstRow = sheet.getRow(0);
            Preconditions.checkNotNull(firstRow, "第一行不能为空");
            short lastCellNum = firstRow.getLastCellNum();
            for (int i = 0; i < lastCellNum; i++) {
                resultList.add(getCellValue(firstRow.getCell(i)));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resultList;
    }

    /**
     * 判断是否空行<br>
     * <p>Title:isEmptyRow<br>
     *
     * @param row excel行
     * @return boolean    返回类型 false不为空，true代表为空
     * @since JDK 1.8
     */
    public static boolean isEmptyTotalRow(Row row) {
        if (row == null) {
            return true;
        }
        List<Boolean> list = new ArrayList<>();
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (null != cell && cell.getCellType() != CellType.BLANK) {
                if (StringUtils.hasText(getCellValue(cell))) {
                    list.add(true);
                } else {
                    list.add(false);
                }

            } else {
                list.add(true);
            }
        }
        return !list.contains(false);
    }

}
