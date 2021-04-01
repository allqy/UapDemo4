package com.szht.service;

import com.szht.entity.User;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity login(User user) throws Exception;
}
