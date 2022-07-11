package com.example.testtask.service;

import com.example.testtask.entity.Attempts;
import com.example.testtask.exception.AttemptsNotFoundException;
import com.example.testtask.repository.AttemptsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttemptsServiceImpl implements AttemptsService{

    private final AttemptsRepository attemptsRepository;

    @Override
    public void saveAttempts(Attempts attempts) {
        attemptsRepository.save(attempts);
    }

    @Override
    public List<Attempts> getAllAttempts() {
        return attemptsRepository.findAll();
    }

    @Override
    public Attempts findById(Integer id) {
        return attemptsRepository.findById(id).orElseThrow(() ->
            new AttemptsNotFoundException("Attempts with id "+ id + " not found"));
    }

    @Override
    public void deleteAttempts(Integer id) {
        Attempts attempts = findById(id);
        attemptsRepository.delete(attempts);
    }

    @Override
    public void updateAttempts(Attempts attempts, Integer id) {
        Attempts attemptsByDB = findById(id);
        attemptsByDB.setTime(attempts.getTime())
                .setCountAttempts(attempts.getCountAttempts());
        attemptsRepository.save(attemptsByDB);
    }

    @Override
    public Attempts getAttemptsBySessionId(String sessionId) {
        return attemptsRepository.getAttemptsByNumberSession(sessionId);
    }
}
