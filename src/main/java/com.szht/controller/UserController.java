package com.szht.controller;

import com.szht.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{ygbh}")
    public ResponseEntity getUserByYgbh(@PathVariable String ygbh){
        return ResponseEntity.ok(userService.queryUserByYgbh(ygbh));
    }

    @GetMapping
    public ResponseEntity getUserByYgbh1(@RequestParam(value = "ygbh")String ygbh){
        return ResponseEntity.ok(userService.queryUserByYgbh(ygbh));
    }
}

