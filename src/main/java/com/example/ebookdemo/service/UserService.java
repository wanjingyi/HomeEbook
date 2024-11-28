package com.example.ebookdemo.service;

import com.example.ebookdemo.domain.User;
import com.example.ebookdemo.domain.UserExample;
import com.example.ebookdemo.mapper.UserMapper;
import com.example.ebookdemo.req.UserQueryReq;
import com.example.ebookdemo.req.UserSaveReq;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.resp.UserQueryResp;
import com.example.ebookdemo.util.CopyUtil;
import com.example.ebookdemo.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> allLists (UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);


        PageInfo<User> pageInfo = new PageInfo<>(userList);

        LOG.info("总条数:{}",pageInfo.getTotal());

        LOG.info("总页数:{}",pageInfo.getPages());


        //列表复制
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        LOG.info("list: {}",list);
        LOG.info("数据: {}",pageResp);

        return pageResp;
    }

    public void saveUser(UserSaveReq userSaveReq) {
        User user = CopyUtil.copy(userSaveReq, User.class);
        if (ObjectUtils.isEmpty(userSaveReq.getId())) {
            //新增
            user.setId(snowFlake.nextId());
            userMapper.insert(user);
        }else {
            //更新
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete (Long id) {
        userMapper.deleteByPrimaryKey(id);
    }
}
