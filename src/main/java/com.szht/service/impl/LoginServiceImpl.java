package com.szht.service.impl;

import com.szht.entity.User;
import com.szht.enums.ExceptionEnum;
import com.szht.exceptions.MyException;
import com.szht.service.LoginService;
import com.szht.service.UserService;
import com.szht.utils.JwtUtil;
import com.szht.utils.MD5Util;
import com.szht.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${uap.jwt.expire}")
    private long expire;

    /**
     * 登录验证方法
     * @param user
     * @return
     */
    public ResponseEntity login(User user) throws Exception {
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
        Map<String, String> usergMap = new HashMap<>();
        usergMap.put("ygbh", user2.getYgbh());
        usergMap.put("ygmc", user2.getYgmc());
        //从redis取出token
        String token = (String) redisUtil.get("uap_ygbh" + user2.getYgbh());
        if(token == null){
            token = jwtUtil.getToken(usergMap);
            log.info("token生成成功，ygbh={}，token={}",user2.getYgbh(),token);
            redisUtil.set("uap_ygbh" + user2.getYgbh(), token,expire);
        }
        //如果redis的token不为空
        return ResponseEntity.ok(user2);
    }
}
