package com.fhcs.clothing_store.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PasswordUtil {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public String encodePassword(String rawPassword) {

        return passwordEncoder.encode(rawPassword);
    }

    public boolean matchesPasswords(String rawPassword, String encodePassword) {
        return passwordEncoder.matches(rawPassword, encodePassword);
    }
}
