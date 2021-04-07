package com.szht.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.szht.entity.User;
import com.szht.mapper.UserMapper;
import com.szht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryUserByYgbh(String ygbh) throws Exception {
        return userMapper.queryUserByYgbh(ygbh);
    }

    public List<User> getAllUsers(int pageIndex,int pageSize) throws Exception {
        PageHelper.startPage(pageIndex, pageSize);
        List<User> userList = userMapper.getAllUsers();
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        return userList;
    }

    public User queryUserByYgbhAndYgmm(String ygbh, String ygmm) throws Exception {
        return userMapper.queryUserByYgbhAndPwd(ygbh,ygmm);
    }
}
