package com.example.testtask.globalAdvice;

import com.example.testtask.exception.AttemptsNotFoundException;
import com.example.testtask.exception.CannotChangeNotYourPet;
import com.example.testtask.exception.PetNotFoundException;
import com.example.testtask.exception.UsernameExistException;
import com.example.testtask.payload.response.MessageError;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MessageError> exceptionHandler(PetNotFoundException exception){
        MessageError messageError = new MessageError();
        messageError.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(messageError);
    }

    @ExceptionHandler
    public ResponseEntity<MessageError> exceptionHandler(AttemptsNotFoundException exception){
        MessageError messageError = new MessageError();
        messageError.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(messageError);
    }

    @ExceptionHandler
    public ResponseEntity<MessageError> exceptionHandler(UsernameNotFoundException exception){
        MessageError messageError = new MessageError();
        messageError.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(messageError);
    }

    @ExceptionHandler
    public ResponseEntity<MessageError> exceptionHandler(UsernameExistException exception){
        MessageError messageError = new MessageError();
        messageError.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(messageError);
    }

    @ExceptionHandler
    public ResponseEntity<MessageError> exceptionHandler(CannotChangeNotYourPet exception){
        MessageError messageError = new MessageError();
        messageError.setMessage(exception.getMessage());
        return ResponseEntity.badRequest().body(messageError);
    }
}
