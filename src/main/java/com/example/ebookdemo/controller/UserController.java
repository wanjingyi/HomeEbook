package com.example.ebookdemo.controller;

import com.example.ebookdemo.req.UserQueryReq;
import com.example.ebookdemo.req.UserSaveReq;
import com.example.ebookdemo.resp.CommonResp;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.resp.UserQueryResp;
import com.example.ebookdemo.service.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @GetMapping("/lists")
    public CommonResp allList(@Valid UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> results = new CommonResp<>();
        PageResp<UserQueryResp> userList = userService.allLists(req);
        results.setContent(userList);
        LOG.info("数据Controller: {}",userList);
        return results;
    }
    
    @PostMapping("/save")
    public CommonResp saveUser(@RequestBody @Valid UserSaveReq user) {
        CommonResp results = new CommonResp<>();
        userService.saveUser(user);
        results.setMessage("数据保存成功");
        LOG.info("数据Controller: {}", user);
        return results;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp deleteUser(@PathVariable Long id) {
        CommonResp results = new CommonResp<>();
       userService.delete(id);
        return results;
    }
}
