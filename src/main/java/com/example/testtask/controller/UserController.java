package com.example.testtask.controller;

import com.example.testtask.globalAdvice.AuthFailureHandler;
import com.example.testtask.globalAdvice.AuthSuccessHandler;
import com.example.testtask.service.AttemptsService;
import com.example.testtask.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/{username}")
    public ResponseEntity<String> authorize(@PathVariable String username){
        return ResponseEntity.ok().body("Hello " + username);
    }
}
