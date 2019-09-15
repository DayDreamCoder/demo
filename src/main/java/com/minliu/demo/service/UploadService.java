package com.minliu.demo.service;

import com.google.common.collect.Maps;
import com.minliu.demo.mapper.StudentMapper;
import com.minliu.demo.pojo.Student;
import com.minliu.demo.util.CommonUtils;
import com.minliu.demo.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: liumin
 * @date: 2019/8/1 23:43
 * @version: JDK1.8
 */
@Service
public class UploadService {

    private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

    private static final Integer SPLIT_LENGTH = 1000;

    private static  AtomicInteger insertCount= new AtomicInteger(1);

    private static List<Row> rows = new ArrayList<>();

    @Resource
    public StudentMapper studentMapper;

    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> upload(MultipartFile file) {
        String filename = file.getOriginalFilename();
        try {
            Workbook workBook = ExcelUtils.getWorkBook(file.getInputStream(), filename);
            Sheet sheet = workBook.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            List<Student> students = new ArrayList<>();
            for (int i = 1; i < lastRowNum + 1; i++) {
                Student student = new Student();
                Row row = sheet.getRow(i);
                rows.add(row);
                logger.info("解析到了{}行", i);
                student.setName(ExcelUtils.getCellValue(row.getCell(0)));
                student.setAge(Integer.parseInt(new DecimalFormat("0").format(row.getCell(1).getNumericCellValue())));
                student.setNickname(ExcelUtils.getCellValue(row.getCell(2)));
                student.setGender(ExcelUtils.getCellValue(row.getCell(3)));
                student.setScore(Integer.parseInt(new DecimalFormat("0").format(row.getCell(4).getNumericCellValue())));
                student.setCountry(ExcelUtils.getCellValue(row.getCell(5)));
                student.setAddress(ExcelUtils.getCellValue(row.getCell(6)));
                student.setClazz(ExcelUtils.getCellValue(row.getCell(7)));
                student.setTeacher(ExcelUtils.getCellValue(row.getCell(8)));
                student.setSchool(ExcelUtils.getCellValue(row.getCell(9)));
                student.setComment(ExcelUtils.getCellValue(row.getCell(10)));
                students.add(student);
            }
            List<List<Student>> lists = CommonUtils.split(students,SPLIT_LENGTH);

            lists.forEach(
                    list ->{
                       logger.info("第{}次插入,插入{}条",insertCount.getAndIncrement(),list.size());
                        studentMapper.batchInsert(list);
                    }
            );

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("Excel 解析错误");
        }
        HashMap<String, String> map = Maps.newHashMap();
        map.put("info", "success");
        return map;
    }


}
