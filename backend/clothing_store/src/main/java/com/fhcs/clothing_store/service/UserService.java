package com.fhcs.clothing_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.request.RegisterRequest;
import com.fhcs.clothing_store.entity.User;
import com.fhcs.clothing_store.repository.UserRepository;
import com.fhcs.clothing_store.util.PasswordUtil;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordUtil passwordUtil;

    public User createUser(@Valid RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        String passwordHash = passwordUtil.encodePassword(request.getPassword());
        user.setPasswordHash(passwordHash);

        return userRepository.save(user);
    }

    public boolean exitsByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user != null) {
            return true;
        }

        return false;
    }

    public boolean exitsByEmail(String email) {

        User user = userRepository.findByEmail(email);

        if (user != null) {
            return true;
        }

        return false;
    }
}
