package com.minliu.demo.controller;

import com.minliu.demo.pojo.Student;
import com.minliu.demo.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: liumin
 * @date: 2019/8/1 23:41
 * @version: JDK1.8
 */
@Api
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;

    @PostMapping("/uploadFile/excel")
    @ApiOperation(value = "文件上传")
    public Map<String,String> upload(@RequestParam("file")MultipartFile file){
        return uploadService.upload(file);
    }
}
