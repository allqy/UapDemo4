package com.szht.service;

import com.szht.entity.User;

import java.util.List;

public interface UserService {
    User queryUserByYgbh(String ygbh) throws Exception;

    List<User> getAllUsers() throws Exception;

    User queryUserByYgbhAndYgmm(String ygbh,String ygmm) throws Exception;
}
