package com.example.demo.exception;

public class DuplicateValidateException extends RuntimeException {
    public DuplicateValidateException(){
        super();
    }

    public DuplicateValidateException(String message){
        super(message);
    }

    public DuplicateValidateException(String message, Throwable cause){
        super(message, cause);
    }

    public DuplicateValidateException(Throwable cause){
        super(cause);
    }
}
