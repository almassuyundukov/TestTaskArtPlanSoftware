package com.example.testtask.exception;

public class CannotChangeNotYourPet extends RuntimeException{
    public CannotChangeNotYourPet(String message){
        super(message);
    }
}
