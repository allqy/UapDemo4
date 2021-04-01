package com.szht.service.impl;

import com.szht.entity.User;
import com.szht.enums.ExceptionEnum;
import com.szht.exceptions.MyException;
import com.szht.service.LoginService;
import com.szht.service.UserService;
import com.szht.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserService userService;

    /**
     * 登录验证方法
     * @param user
     * @return
     */
    public ResponseEntity login(User user) throws Exception {
        System.out.println("user="+user);
        if(user==null || StringUtils.isBlank(user.getYgbh()) || StringUtils.isBlank(user.getYgmm())){
            log.info("登录验证方法参数user不能为空");
            throw new MyException(ExceptionEnum.PARAMS_NOT_MATCH);
        }
        User user1 = userService.queryUserByYgbh(user.getYgbh());
        if(user1==null){
            log.info("用户不存在");
            throw new MyException(ExceptionEnum.USER_NOT_EXIST);
        }
        User user2= userService.queryUserByYgbhAndYgmm(user.getYgbh(), MD5Util.MD5Encode(user.getYgmm(),null));
        if(user2 ==null){
            log.info("用户名或密码不正确");
            throw new MyException(ExceptionEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        log.info("登录成功!!!");
        return ResponseEntity.ok(user2);
    }
}
