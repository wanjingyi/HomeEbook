package com.example.ebookdemo.controller;

import com.example.ebookdemo.req.EbookQueryReq;
import com.example.ebookdemo.req.EbookSaveReq;
import com.example.ebookdemo.resp.CommonResp;
import com.example.ebookdemo.resp.EbookQueryResp;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.service.EbookService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/ebook")
public class EbookController {
    private static final Logger LOG = LoggerFactory.getLogger(EbookController.class);
    @Resource
    private EbookService ebookService;

    @GetMapping("/lists")
    public CommonResp allList(@Valid EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> results = new CommonResp<>();
        PageResp<EbookQueryResp> ebookList = ebookService.allLists(req);
        results.setContent(ebookList);
        LOG.info("数据Controller: {}",ebookList);
        return results;
    }
    
    @PostMapping("/save")
    public CommonResp saveEbook(@RequestBody @Valid EbookSaveReq ebook) {
        CommonResp results = new CommonResp<>();
        ebookService.saveEbook(ebook);
        results.setMessage("数据保存成功");
        LOG.info("数据Controller: {}", ebook);
        return results;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp deleteEbook(@PathVariable Long id) {
        CommonResp results = new CommonResp<>();
       ebookService.delete(id);
        return results;
    }
}
