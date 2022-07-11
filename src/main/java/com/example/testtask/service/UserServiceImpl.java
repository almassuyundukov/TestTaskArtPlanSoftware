package com.example.testtask.service;

import com.example.testtask.entity.Pet;
import com.example.testtask.entity.Role;
import com.example.testtask.entity.User;
import com.example.testtask.exception.UsernameExistException;
import com.example.testtask.repository.RoleRepository;
import com.example.testtask.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    List<Pet> pets = new ArrayList<>();

    @Override
    public boolean create(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null){
            return false;
        }

        boolean existsRoleByName = roleRepository.existsRoleByName("ROLE_USER");
        if (!existsRoleByName){
            Role role = new Role(1, "ROLE_USER");
            roleRepository.save(role);
        }

        String sha3HexPassword = new org.apache.commons.codec.digest.DigestUtils("SHA3-256").digestAsHex(user.getPassword());

        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")))
                .setPassword(sha3HexPassword)
                .setEnabled(true)
                .setPets(pets)
                .setUsername(user.getUsername());
        userRepository.save(user);
        return true;
    }

    @Override
    public void validate(String username) {
        if (userRepository.existsUsersByUsername(username)) throw new
                UsernameExistException("User with this nickname " + username + " is exist");
    }

    @Override
    public boolean authorization(String username, String password) {
        String sha3HexPassword = new org.apache.commons.codec.digest.DigestUtils("SHA3-256").digestAsHex(password);
        return userRepository.existsUserByUsernameAndPassword(username, sha3HexPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserAndRolesByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

}
