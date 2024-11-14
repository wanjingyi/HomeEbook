package com.example.ebookdemo.controller;


import com.example.ebookdemo.domain.Demo;
import com.example.ebookdemo.domain.Test;
import com.example.ebookdemo.service.DemoService;
import com.example.ebookdemo.service.TestService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;

    @GetMapping("/controller")
    public List<Demo> list () {
        return demoService.list();
    }
}
