package com.example.testtask.service;

import com.example.testtask.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService{
    boolean create(User user);
    void validate(String username);
    boolean authorization(String username, String password);
    UserDetails loadUserByUsername(String username);
}
