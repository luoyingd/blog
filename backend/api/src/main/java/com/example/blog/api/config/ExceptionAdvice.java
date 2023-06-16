package com.example.blog.api.config;

import com.example.blog.base.exception.BlogException;
import com.example.blog.base.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            return R.error(exception.getMessage());
        }
        else if (e instanceof BlogException) {
            log.error("BlogException", e);
            BlogException exception = (BlogException) e;
            return R.error(exception.getException().getCode(), exception.getException().getMsg());
        }
        else {
            log.error("Runtime Error", e);
            return R.error("service error");
        }
    }
}