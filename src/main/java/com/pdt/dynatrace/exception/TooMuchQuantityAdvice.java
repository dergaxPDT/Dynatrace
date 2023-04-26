package com.pdt.dynatrace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TooMuchQuantityAdvice {
    @ResponseBody
    @ExceptionHandler(EnumConstantNotPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String tooMuch(TooMuchQuantityException ex){
        return ex.getMessage();
    }
}
