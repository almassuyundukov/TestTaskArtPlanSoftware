package com.example.testtask.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/pet/**").authenticated()
                    .anyRequest().permitAll()
                .and()
                    .httpBasic()
                .and()
                    .logout()
                    .logoutSuccessUrl("/");
        http.csrf().disable();
    }
}
