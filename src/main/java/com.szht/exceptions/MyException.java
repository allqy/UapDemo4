package com.szht.exceptions;

import com.szht.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
