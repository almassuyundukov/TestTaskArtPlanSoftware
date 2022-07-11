package com.example.testtask.repository;

import com.example.testtask.entity.Attempts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttemptsRepository extends JpaRepository<Attempts, Integer> {
    Attempts getAttemptsByNumberSession(String idSession);
}
