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
    public ResponseEntity getAllUsers(@RequestParam(value="pageIndex",required = true,defaultValue = "1") int pageIndex, @RequestParam(value="pageSize",defaultValue = "10") int pageSize) throws Exception {
        return ResponseEntity.ok(userService.getAllUsers(pageIndex,pageSize));
    }

    @GetMapping("/{ygbh}")
    public ResponseEntity getUserByYgbh(@PathVariable String ygbh) throws Exception {
        return ResponseEntity.ok(userService.queryUserByYgbh(ygbh));
    }

    @GetMapping
    public ResponseEntity getUserByYgbh1(@RequestParam(value = "ygbh")String ygbh) throws Exception {
        return ResponseEntity.ok(userService.queryUserByYgbh(ygbh));
    }
}

