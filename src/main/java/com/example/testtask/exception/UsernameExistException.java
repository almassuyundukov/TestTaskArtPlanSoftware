package com.example.testtask.exception;

public class UsernameExistException extends RuntimeException{
    public UsernameExistException(String message){
        super(message);
    }
}
