package com.szht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    PARAMS_NOT_MATCH(400,"参数不匹配")
    ;
    private int code;
    private String message;
}
