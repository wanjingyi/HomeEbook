package com.example.ebookdemo.controller;


import com.example.ebookdemo.req.DocumentQueryReq;
import com.example.ebookdemo.req.DocumentSaveReq;
import com.example.ebookdemo.resp.CommonResp;
import com.example.ebookdemo.resp.DocumentQueryResp;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.service.DocumentService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/document")
public class DocumentController {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentController.class);
    @Resource
    private DocumentService documentService;

    @GetMapping("/lists")
    public CommonResp allList(@Valid DocumentQueryReq req) {
        CommonResp<PageResp<DocumentQueryResp>> results = new CommonResp<>();
        PageResp<DocumentQueryResp> documentList = documentService.allLists(req);
        results.setContent(documentList);
        LOG.info("数据Controller: {}",documentList);
        return results;
    }
    
    @PostMapping("/save")
    public CommonResp saveDocument(@RequestBody @Valid DocumentSaveReq document) {
        CommonResp results = new CommonResp<>();
        documentService.saveDocument(document);
        results.setMessage("数据保存成功");
        LOG.info("数据Controller: {}", document);
        return results;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp deleteDocument(@PathVariable Long id) {
        CommonResp results = new CommonResp<>();
       documentService.delete(id);
        return results;
    }

    @GetMapping("/allData")
    public CommonResp allData() {
        CommonResp<List<DocumentQueryResp>> results = new CommonResp<>();
        List<DocumentQueryResp> documentList = documentService.allData();
        results.setContent(documentList);
        LOG.info("数据Controller: {}",documentList);
        return results;
    }
}
