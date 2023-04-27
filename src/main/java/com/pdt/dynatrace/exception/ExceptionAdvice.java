package com.pdt.dynatrace.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFound(NotFoundException ex) {
        log.warn(ex.getMsg());
        return ex.getMsg();
    }

    @ResponseBody
    @ExceptionHandler(EnumConstantNotPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String tooMuch(TooMuchQuantityException ex){
        log.warn(ex.getMessage());
        return ex.getMessage();
    }
}
