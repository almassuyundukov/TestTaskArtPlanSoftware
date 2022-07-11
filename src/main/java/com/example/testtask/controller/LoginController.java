package com.example.testtask.controller;

import com.example.testtask.entity.Attempts;
import com.example.testtask.entity.User;
import com.example.testtask.globalAdvice.AuthFailureHandler;
import com.example.testtask.globalAdvice.AuthSuccessHandler;
import com.example.testtask.service.AttemptsService;
import com.example.testtask.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final UserServiceImpl userService;
    private final AttemptsService attemptsService;
    private final AuthFailureHandler authFailureHandler;
    private final AuthSuccessHandler authSuccessHandler;

    @PostMapping("/create")
    public void createUser(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) throws IOException, ServletException {
        userService.validate(user.getUsername());
        userService.create(user);
        User userFromDB = (User) userService.loadUserByUsername(user.getUsername());
        String username = userFromDB.getUsername();
        String password = userFromDB.getPassword();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userFromDB.getAuthorities();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, authorities);
        SecurityContextHolder.getContext().setAuthentication(authRequest);
        authSuccessHandler.onAuthenticationSuccess(request, response, authRequest);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateNickname(@RequestBody String username){
        userService.validate(username);
        return ResponseEntity.ok().body("This nickname is available");
    }

    @PostMapping("/login")
    public void authorization(HttpServletRequest request, HttpServletResponse response, @RequestBody User user) throws IOException, ServletException {
        String username = user.getUsername();
        String password = user.getPassword();
        User userFromDB = (User) userService.loadUserByUsername(username);
        boolean authorization = userService.authorization(username, password);
        if (authorization){
            Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) userFromDB.getAuthorities();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password, authorities);
            SecurityContextHolder.getContext().setAuthentication(authRequest);
            authSuccessHandler.onAuthenticationSuccess(request, response, authRequest);
            return;
        }
        authFailureHandler.onAuthenticationFailure(request, response, new AuthenticationException("") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        });
    }

    @GetMapping("/unauthorized")
    public ResponseEntity<String> unauthorized(HttpServletRequest request){
        String idSession = request.getSession().getId();
        Attempts attemptsByNumberSession = attemptsService.getAttemptsBySessionId(idSession);
        Integer countAttempts = attemptsByNumberSession.getCountAttempts();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized " + countAttempts);
    }

    @GetMapping("/authorization-limit")
    public ResponseEntity<String> authorizationLimit(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You have reached the limit of authorization attempts");
    }
}
