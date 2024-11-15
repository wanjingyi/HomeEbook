package com.example.ebookdemo.service;

import com.example.ebookdemo.domain.Ebook;
import com.example.ebookdemo.mapper.EbookMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<Ebook> allLists () {
        return ebookMapper.selectByExample(null);
    }
}
