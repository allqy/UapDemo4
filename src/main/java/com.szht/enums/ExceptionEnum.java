package com.szht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    PARAMS_NOT_MATCH(400,"参数不匹配"),
    NO_PERMISSION_ERROR(400,"没有权限，请重新登录"),
    USER_NOT_EXIST(404,"用户不存在"),
    INVALID_TOKEN(401,"无效凭证，请重新登录"),
    TOKEN_NEED_REFRESH(400,"无效凭证，请退出重试或者使用最新的凭证"),
    TOKEN_EXPIRED(400,"登录已过期，请重新登录"),
    USERNAME_OR_PASSWORD_ERROR(500,"用户名或密码不正确"),
    SYSTEM_INTERNAL_ERROR(500,"系统内部异常")
    ;
    private int code;
    private String message;
}
