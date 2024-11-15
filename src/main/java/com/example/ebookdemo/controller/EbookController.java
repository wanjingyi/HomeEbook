package com.example.ebookdemo.controller;

import com.example.ebookdemo.domain.Ebook;
import com.example.ebookdemo.req.EbookReq;
import com.example.ebookdemo.resp.CommonResp;
import com.example.ebookdemo.resp.EbookResp;
import com.example.ebookdemo.service.EbookService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/lists")
    public CommonResp allList(EbookReq req) {
        CommonResp<List<EbookResp>> results = new CommonResp<>();
        List<EbookResp> ebookList = ebookService.allLists(req);
        results.setContent(ebookList);
        return results;
    }
}
