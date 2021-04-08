package com.szht.service;

import com.github.pagehelper.PageInfo;
import com.szht.entity.User;

public interface UserService {
    User queryUserByYgbh(String ygbh) throws Exception;

    /**
     * 分页查询所有用户
     * @param pageIndex 当前页数
     * @param pageSize 每页数量
     * @return
     * @throws Exception
     */
    PageInfo<User> getAllUsers(int pageIndex, int pageSize) throws Exception;

    User queryUserByYgbhAndYgmm(String ygbh,String ygmm) throws Exception;
}
