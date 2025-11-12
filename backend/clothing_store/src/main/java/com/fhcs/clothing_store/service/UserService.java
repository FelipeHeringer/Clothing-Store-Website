package com.fhcs.clothing_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.UserPatchDto;
import com.fhcs.clothing_store.dto.request.RegisterRequest;
import com.fhcs.clothing_store.entity.User;
import com.fhcs.clothing_store.repository.UserRepository;
import com.fhcs.clothing_store.util.JsonPatchUtil;
import com.fhcs.clothing_store.util.JwtTokenUtil;
import com.fhcs.clothing_store.util.PasswordUtil;
import com.github.fge.jsonpatch.JsonPatch;

import jakarta.validation.Valid;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private JsonPatchUtil jsonPatchUtil;

    public User createUser(@Valid RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        String passwordHash = passwordUtil.encodePassword(request.getPassword());
        user.setPasswordHash(passwordHash);

        return userRepository.save(user);
    }

    public User getUserInformation(String token) throws Exception {

        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token é obrigatório");
        }

        String username = jwtTokenUtil.extractUsername(token);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        return user;
    }

    public User updateUserInformation(String token, JsonPatch patch) throws Exception {

        try {
            if (token == null || token.isEmpty()) {
                throw new IllegalArgumentException("Token é obrigatório");
            }

            String username = jwtTokenUtil.extractUsername(token);
            User user = userRepository.findByUsername(username);

            if (user == null) {
                throw new IllegalArgumentException("Usuário não encontrado");
            }

            UserPatchDto userPatched = jsonPatchUtil.extractPatchedFields(patch, UserPatchDto.class);
            applyChangesToUser(user, userPatched);

            return userRepository.save(user);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar informações do usuário: " + e.getMessage());
        }
    }

    public void deleteUser(String accessToken) throws Exception {
        User user = getUserInformation(accessToken);
        userRepository.delete(user);
    }

    private void applyChangesToUser(User user, UserPatchDto userPatched) {
        try {
            verifyUsernameAndEmailUniqueness(user, userPatched);
            if (userPatched.getUsername() != null && !userPatched.getUsername().isEmpty()) {
                user.setUsername(userPatched.getUsername());
            }
            if (userPatched.getEmail() != null && !userPatched.getEmail().isEmpty()) {
                user.setEmail(userPatched.getEmail());
            }
            passwordUtil.verifyAndHashPasswordIfChanged(user, userPatched);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar informações do usuário: " + e.getMessage());
        }
    }

    private void verifyUsernameAndEmailUniqueness(User user, UserPatchDto userPatched) {
        if (userPatched.getUsername() != null && !userPatched.getUsername().isEmpty()) {
            if (exitsByUsername(userPatched.getUsername())) {
                throw new IllegalArgumentException("Nome de usuário já está em uso");
            }
        }

        if (userPatched.getEmail() != null && !userPatched.getEmail().isEmpty()) {
            if (exitsByEmail(userPatched.getEmail())) {
                throw new IllegalArgumentException("Email já está em uso");
            }
        }
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
