package com.example.testtask.service;


import com.example.testtask.entity.Attempts;

import java.util.List;

public interface AttemptsService{
    void saveAttempts(Attempts attempts);
    List<Attempts> getAllAttempts();
    Attempts findById(Integer id);
    void deleteAttempts(Integer id);
    void updateAttempts(Attempts attempts, Integer id);
    Attempts getAttemptsBySessionId(String sessionId);

}
