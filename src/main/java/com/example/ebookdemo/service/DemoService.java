package com.example.ebookdemo.service;

import com.example.ebookdemo.domain.Demo;
import com.example.ebookdemo.domain.Test;
import com.example.ebookdemo.mapper.DemoMapper;
import com.example.ebookdemo.mapper.TestMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list() {
        return demoMapper.selectByExample(null);
    }
}
