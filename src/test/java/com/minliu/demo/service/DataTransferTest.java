package com.minliu.demo.service;

import com.google.common.collect.Lists;
import com.minliu.demo.mapper.ItemMapper;
import com.minliu.demo.pojo.Item;
import com.minliu.demo.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DataTransferTest <br>
 * date: 2:57 PM 12/03/2019 <br>
 *
 * @author: liumin
 * @version: 0.0.1-SNAPSHOT
 * @since: JDK 1.8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataTransferTest {
    private static final Logger logger = LoggerFactory.getLogger(DataTransferTest.class);

    @Resource
    private ItemMapper itemMapper;

    @Test
    public void test() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("PARSE EXCEL");
        File file = new File("C:\\Users\\minliu\\Desktop\\item.xlsx");
        Workbook workBook = ExcelUtils.getWorkBook(file);
        Sheet sheet = workBook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        List<Item> items = new ArrayList<>();
        Cell cell;
        for (int i = 0; i <= lastRowNum + 1; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            Item item = new Item();
            if ((cell = row.getCell(0)) != null) {
                item.setName(ExcelUtils.getCellValue(cell));
            }
            if ((cell = row.getCell(1)) != null) {
                item.setLink(ExcelUtils.getCellValue(cell));
            }
            if ((cell = row.getCell(2)) != null) {
                item.setPrice(new BigDecimal(ExcelUtils.getCellValue(cell)));
            }
            if ((cell = row.getCell(3)) != null) {
                item.setStore(ExcelUtils.getCellValue(cell));
            }
            if ((cell = row.getCell(4)) != null) {
                String cellValue = ExcelUtils.getCellValue(cell);
                if (cellValue.contains(".")){
                    cellValue = cellValue.split("\\.")[0];
                }
                item.setComments(Integer.parseInt(cellValue));
            }
            if ((cell = row.getCell(5)) != null) {
                item.setGood(new BigDecimal(ExcelUtils.getCellValue(cell)));
            }
            items.add(item);
        }
        stopWatch.stop();
        logger.info(stopWatch.prettyPrint());
        logger.info("TOTAL:{}", items.size());
        List<List<Item>> lists = Lists.partition(items, 1000);
        long before = System.currentTimeMillis();
        lists.stream().parallel().forEach(dataList -> itemMapper.batchInsert(dataList));
        logger.info("COSTS:{}",System.currentTimeMillis() - before);
    }
}
