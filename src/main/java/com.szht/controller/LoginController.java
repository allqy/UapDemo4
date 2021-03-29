package com.szht.controller;

import com.szht.service.LoginService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping(value = "/validateCode")
    public ResponseEntity getValidateCode(HttpServletRequest request, HttpServletResponse response){
        return  ResponseEntity.ok(loginService.getValidateCode(request,response));
    }
}
