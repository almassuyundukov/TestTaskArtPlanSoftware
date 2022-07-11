package com.example.testtask.repository;

import com.example.testtask.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsRoleByName(String name);
}
