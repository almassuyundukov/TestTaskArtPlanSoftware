package com.example.testtask.repository;

import com.example.testtask.entity.Pet;
import com.example.testtask.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    List<Pet> findPetByUser(User user);
}
