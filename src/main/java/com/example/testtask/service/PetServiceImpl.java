package com.example.testtask.service;

import com.example.testtask.entity.Pet;
import com.example.testtask.entity.User;
import com.example.testtask.exception.CannotChangeNotYourPet;
import com.example.testtask.exception.PetNotFoundException;
import com.example.testtask.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Override
    public Pet findById(Integer id) {
        return petRepository.findById(id)
                .orElseThrow(() ->
                        new PetNotFoundException("Pet with id " + id + " not found"));
    }

    @Override
    public List<Pet> findPetsByUser(User user) {
        List<Pet> pets = petRepository.findPetByUser(user);
        if (pets.isEmpty()) {
            throw new PetNotFoundException("Pets not found in database");
        }
        return pets;
    }

    @Override
    public void save(Pet pet, User user) {
        pet.setUser(user);
        petRepository.save(pet);
    }

    @Override
    public void update(User user, Pet pet, Integer id) {
        Pet petOld = findById(id);
        Integer idUserByPet = petOld.getUser().getId();
        if (idUserByPet.equals(user.getId())) {
            petOld.setCharacter(pet.getCharacter())
                    .setDateOfBirth(pet.getDateOfBirth())
                    .setNickname(pet.getNickname())
                    .setSpecies(pet.getSpecies());
            petRepository.save(petOld);
        } else {
            throw new CannotChangeNotYourPet("Can't change someone else's pet");
        }
    }

    @Override
    public void delete(User user, Integer id) {
        Pet pet = findById(id);
        Integer idUserByPet = pet.getUser().getId();
        if (idUserByPet.equals(user.getId())) {
            petRepository.delete(pet);
        } else throw new CannotChangeNotYourPet("Can't change someone else's pet");
    }
}
