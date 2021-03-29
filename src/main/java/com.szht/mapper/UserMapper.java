package com.szht.mapper;

import com.szht.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public List<User> getAllUsers();
    public User queryUserByYgbh(String ygbh);
}
