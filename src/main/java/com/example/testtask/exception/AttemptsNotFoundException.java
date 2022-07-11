package com.example.testtask.exception;

public class AttemptsNotFoundException extends RuntimeException{
    public AttemptsNotFoundException(String message){
        super(message);
    }
}
