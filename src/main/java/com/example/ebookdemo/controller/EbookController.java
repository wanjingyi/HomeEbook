package com.example.ebookdemo.controller;

import com.example.ebookdemo.domain.Ebook;
import com.example.ebookdemo.resp.CommonResp;
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
    public CommonResp<List<Ebook>> allList() {
        CommonResp<List<Ebook>> results = new CommonResp<>();
        List<Ebook> ebookList = ebookService.allLists();
        results.setContent(ebookList);
        return results;
    }
}
