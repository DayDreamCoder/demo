package com.minliu.demo.controller;

import com.minliu.demo.common.ResponseEnum;
import com.minliu.demo.common.WebResponse;
import com.minliu.demo.pojo.Item;
import com.minliu.demo.service.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 商品控制层
 *
 * @author: liumin
 * @date: 2019/4/8 20:33
 * @version: JDK1.8
 */
@Api(tags = "商品模块")
@RestController
@RequestMapping("/api/item")
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Resource
    private ItemService itemService;

    @ApiOperation(value = "根据Id查询商品")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROOT')")
    public WebResponse findById(@PathVariable Integer id){
        Item item = itemService.getItemById(id);
        WebResponse response = new WebResponse(ResponseEnum.SUCCESS);
        response.putData("item",item);
        return response;
    }
}
