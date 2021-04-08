package com.szht.mapper;

import com.szht.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> getAllUsers() throws Exception;
    User queryUserByYgbh(String ygbh) throws Exception;
    User queryUserByYgbhAndPwd(@Param("ygbh") String ygbh, @Param("ygmm")String ygmm) throws Exception;
}
