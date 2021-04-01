package com.szht.service.impl;

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

    public List<User> getAllUsers() throws Exception {
        return userMapper.getAllUsers();
    }

    public User queryUserByYgbhAndYgmm(String ygbh, String ygmm) throws Exception {
        return userMapper.queryUserByYgbhAndPwd(ygbh,ygmm);
    }
}
