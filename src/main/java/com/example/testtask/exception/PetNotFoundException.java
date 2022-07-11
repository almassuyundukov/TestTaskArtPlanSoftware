package com.example.testtask.exception;

public class PetNotFoundException extends RuntimeException{
    public PetNotFoundException(String message){
        super(message);
    }
}
