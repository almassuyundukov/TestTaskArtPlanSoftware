package com.example.testtask.repository;

import com.example.testtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUsersByUsername(String username);
    boolean existsUserByUsernameAndPassword(String username, String password);
    User findByUsername (String username);
    User getUserAndRolesByUsername(String username);
}
