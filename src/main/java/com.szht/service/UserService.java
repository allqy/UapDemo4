package com.szht.service;

import com.szht.entity.User;
import com.szht.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User queryUserByYgbh(String ygbh){
        return userMapper.queryUserByYgbh(ygbh);
    }

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }


}
