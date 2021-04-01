package com.szht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    PARAMS_NOT_MATCH(400,"参数不匹配"),
    USER_NOT_EXIST(404,"用户不存在"),
    USERNAME_OR_PASSWORD_ERROR(500,"用户名或密码不正确"),
    SYSTEM_INTERNAL_ERROR(500,"系统内部异常")
    ;
    private int code;
    private String message;
}
