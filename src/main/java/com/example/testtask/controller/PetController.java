package com.example.testtask.controller;

import com.example.testtask.entity.Pet;
import com.example.testtask.entity.User;
import com.example.testtask.service.PetService;
import com.example.testtask.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Pet> findById(@PathVariable Integer id){
        return ResponseEntity.ok().body(petService.findById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<Pet>> findAll(Authentication authentication) {
        String username = authentication.getPrincipal().toString();
        User user = (User)userService.loadUserByUsername(username);
        return ResponseEntity.ok().body(petService.findPetsByUser(user));
    }

    @PostMapping("/")
    public ResponseEntity<String> savePet(@RequestBody Pet pet, Authentication authentication){
        String username = authentication.getPrincipal().toString();
        User user = (User)userService.loadUserByUsername(username);
        petService.save(pet, user);
        return ResponseEntity.ok().body("Pet is created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePet(@RequestBody Pet pet, @PathVariable Integer id, Authentication authentication){
        String username = authentication.getPrincipal().toString();
        User user = (User)userService.loadUserByUsername(username);
        petService.update(user, pet,id);
        return ResponseEntity.ok().body("Pet is updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Integer id, Authentication authentication){
        String username = authentication.getPrincipal().toString();
        User user = (User)userService.loadUserByUsername(username);
        petService.delete(user, id);
        return ResponseEntity.ok().body("Pet is deleted");
    }
}
