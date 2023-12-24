package com.example.hci.exception;

import lombok.Data;

@Data
public class MyException extends RuntimeException {
    private String code;
    public MyException(String message, Throwable root) {
        super(message, root);
    }
    public MyException(String code, String message) {
        super(message);
        this.code = code;
    }
}
