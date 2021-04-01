package com.szht.controller;

import com.szht.entity.User;
import com.szht.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity testLogin(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(loginService.login(user));
    }
}
