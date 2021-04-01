package com.szht.advice;

import com.szht.enums.ExceptionEnum;
import com.szht.exceptions.MyException;
import com.szht.responseEntity.ExceptionResult;
import com.szht.service.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(MyException.class)
    public ResponseEntity<ExceptionResult> handleMyException(MyException e){
        return ResponseEntity.status(e.getExceptionEnum().getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResult> handleMyException(Exception e){
        log.info("系统内部异常，异常信息{}",e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResult(ExceptionEnum.SYSTEM_INTERNAL_ERROR));
    }
}
