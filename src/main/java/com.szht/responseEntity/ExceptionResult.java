package com.szht.responseEntity;

import com.szht.enums.ExceptionEnum;
import lombok.Data;

@Data
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em){
        this.status=em.getCode();
        this.message=em.getMessage();
        this.timestamp=System.currentTimeMillis();
    }
}
