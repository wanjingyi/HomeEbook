package com.example.ebookdemo.service;

import com.example.ebookdemo.domain.User;
import com.example.ebookdemo.domain.UserExample;
import com.example.ebookdemo.exception.BusinessException;
import com.example.ebookdemo.exception.BusinessExceptionCode;
import com.example.ebookdemo.mapper.UserMapper;
import com.example.ebookdemo.req.UserLoginReq;
import com.example.ebookdemo.req.UserQueryReq;
import com.example.ebookdemo.req.UserRestPasswordReq;
import com.example.ebookdemo.req.UserSaveReq;
import com.example.ebookdemo.resp.PageResp;
import com.example.ebookdemo.resp.UserLoginResp;
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
            User userDB = selectByLoginName(user.getLoginName());
            LOG.info("数据库用户：{}",userDB);
            if (ObjectUtils.isEmpty(userDB)) {
                //新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            }else {
                //用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }
        }else {
            //更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    public void resetPassword(UserRestPasswordReq req) {
        User user = CopyUtil.copy(req, User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void delete (Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList!= null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    public UserLoginResp login (UserLoginReq req) {
        User userDB = selectByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDB)) {
            //用户名不存在
            LOG.info("Login:{}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }else {
            if (!userDB.getPassword().equals(req.getPassword())) {
                //账号，密码不对
                LOG.info("Login:{}", req.getLoginName());
                LOG.info("请求密码Password:{}", req.getPassword());
                LOG.info("数据库密码DBPassword:{}", userDB.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }else {
                //
                UserLoginResp userLoginResp = CopyUtil.copy(userDB, UserLoginResp.class);
                return userLoginResp;
            }
        }
    }
}
