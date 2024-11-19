package com.example.ebookdemo.controller;

import com.example.ebookdemo.domain.Ebook;
import com.example.ebookdemo.req.EbookReq;
import com.example.ebookdemo.resp.CommonResp;
import com.example.ebookdemo.resp.EbookResp;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.service.EbookService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/ebook")
public class EbookController {
    private static final Logger LOG = LoggerFactory.getLogger(EbookController.class);
    @Resource
    private EbookService ebookService;

    @GetMapping("/lists")
    public CommonResp allList(EbookReq req) {
        CommonResp<PageResp<EbookResp>> results = new CommonResp<>();
        PageResp<EbookResp> ebookList = ebookService.allLists(req);
        results.setContent(ebookList);
        LOG.info("数据Controller: {}",ebookList);
        return results;
    }
}
