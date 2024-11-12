package com.example.ebookdemo.service;

import com.example.ebookdemo.domain.Test;
import com.example.ebookdemo.mapper.TestMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    public List<Test> list() {
        return testMapper.list();
    }
}
