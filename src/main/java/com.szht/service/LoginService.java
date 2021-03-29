package com.szht.service;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class LoginService {
    /**
     * 生成验证码图片
     * @param request
     * @param response
     * @return
     */
    public String getValidateCode(HttpServletRequest request, HttpServletResponse response) {

        return null;
    }
}
