package com.example.ebookdemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.ebookdemo.req.UserLoginReq;
import com.example.ebookdemo.req.UserQueryReq;
import com.example.ebookdemo.req.UserRestPasswordReq;
import com.example.ebookdemo.req.UserSaveReq;
import com.example.ebookdemo.resp.CommonResp;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.resp.UserLoginResp;
import com.example.ebookdemo.resp.UserQueryResp;
import com.example.ebookdemo.service.UserService;
import com.example.ebookdemo.util.SnowFlake;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    @Resource
    private UserService userService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private RedisTemplate redisTemplate;
    
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
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        CommonResp results = new CommonResp<>();
        userService.saveUser(user);
        results.setMessage("数据保存成功");
        return results;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@RequestBody @Valid UserRestPasswordReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp<>();
        userService.resetPassword(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp deleteUser(@PathVariable Long id) {
        CommonResp results = new CommonResp<>();
       userService.delete(id);
        return results;
    }

    @PostMapping("/login")
    public CommonResp login(@RequestBody @Valid UserLoginReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp userLoginResp = userService.login(req);
        //生成单点登陆token，并放入redis中
        Long token = snowFlake.nextId();
        LOG.info("生成单点登陆token：{},并存入redis",token);
        userLoginResp.setToken(token.toString());
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp),3600 * 24 , TimeUnit.SECONDS);
        resp.setContent(userLoginResp);
        return resp;
    }

    @GetMapping ("/loginout/{token}")
    public CommonResp loginout(@PathVariable String token) {
        CommonResp results = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token: {}",token);
        return results;
    }
}
