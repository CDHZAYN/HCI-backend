package com.example.hci.exception;

import com.example.hci.common.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = {"com.example.controller"})
public class MyExceptionHandler {
    @ExceptionHandler(MyException.class)
    @ResponseBody
    private Response handleMyServiceException(MyException e) {
        return new Response(e.getCode(), e.getMessage());
    }
}
