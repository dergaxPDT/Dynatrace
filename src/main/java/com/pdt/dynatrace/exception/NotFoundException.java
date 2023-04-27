package com.pdt.dynatrace.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private String msg;

    NotFoundException(String msg){
        this.msg=msg;
    }
}
