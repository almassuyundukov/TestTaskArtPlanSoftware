package com.example.testtask.service;

import com.example.testtask.entity.Pet;
import com.example.testtask.entity.User;

import java.util.List;

public interface PetService {
    Pet findById(Integer id);
    List<Pet> findPetsByUser(User user);
    void save(Pet pet, User user);
    void update(User user, Pet pet, Integer id);
    void delete(User user, Integer id);
}
