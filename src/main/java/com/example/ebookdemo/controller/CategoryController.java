package com.example.ebookdemo.controller;


import com.example.ebookdemo.req.CategoryQueryReq;
import com.example.ebookdemo.req.CategorySaveReq;
import com.example.ebookdemo.resp.CategoryQueryResp;
import com.example.ebookdemo.resp.CommonResp;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.service.CategoryService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryController {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);
    @Resource
    private CategoryService categoryService;

    @GetMapping("/lists")
    public CommonResp allList(@Valid CategoryQueryReq req) {
        CommonResp<PageResp<CategoryQueryResp>> results = new CommonResp<>();
        PageResp<CategoryQueryResp> categoryList = categoryService.allLists(req);
        results.setContent(categoryList);
        LOG.info("数据Controller: {}",categoryList);
        return results;
    }
    
    @PostMapping("/save")
    public CommonResp saveCategory(@RequestBody @Valid CategorySaveReq category) {
        CommonResp results = new CommonResp<>();
        categoryService.saveCategory(category);
        results.setMessage("数据保存成功");
        LOG.info("数据Controller: {}", category);
        return results;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp deleteCategory(@PathVariable Long id) {
        CommonResp results = new CommonResp<>();
       categoryService.delete(id);
        return results;
    }

    @GetMapping("/allData")
    public CommonResp allData() {
        CommonResp<List<CategoryQueryResp>> results = new CommonResp<>();
        List<CategoryQueryResp> categoryList = categoryService.allData();
        results.setContent(categoryList);
        LOG.info("数据Controller: {}",categoryList);
        return results;
    }
}
