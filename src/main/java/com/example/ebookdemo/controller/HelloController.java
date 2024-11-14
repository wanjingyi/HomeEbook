package com.example.ebookdemo.controller;


import com.example.ebookdemo.domain.Test;
import com.example.ebookdemo.service.TestService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    @Value("${name}")
    private String name;

    @Resource
    private TestService testService;

    @GetMapping("/test/name")
    public String getName() {
        return name;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, EBook Demo!";
    }

    @GetMapping("/test")
    public List<Test> test() {
        return testService.list();
    }
}
