package com.szht.service;

import com.szht.entity.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface LoginService {
    ResponseEntity login(User user,HttpServletResponse response) throws Exception;
}
