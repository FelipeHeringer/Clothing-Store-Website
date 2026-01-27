package com.fhcs.clothing_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.request.LoginRequest;
import com.fhcs.clothing_store.dto.request.RegisterRequest;
import com.fhcs.clothing_store.dto.response.AuthResponse;
import com.fhcs.clothing_store.entity.User;
import com.fhcs.clothing_store.exception.InvalidTokenException;
import com.fhcs.clothing_store.repository.UserRepository;
import com.fhcs.clothing_store.security.UserDetailsImpl;
import com.fhcs.clothing_store.util.JwtTokenUtil;
import com.fhcs.clothing_store.util.PasswordUtil;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserService userService;

    @Autowired 
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    public AuthResponse register(RegisterRequest registerRequest) {

        if (userService.exitsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Usuário já existe");
        }

        if (userService.exitsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Usuário já existe");
        }

        User user = userService.createUser(registerRequest);

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
        refreshTokenService.createRefreshToken(user,refreshToken);
        Long accessTokenExpiresIn = jwtTokenUtil.getAccessTokenExpiration(accessToken);

        return AuthResponse.success(accessToken, refreshToken, accessTokenExpiresIn, user);

    }

    public AuthResponse login(LoginRequest loginRequest) {
        try {
            User user = authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());

            UserDetailsImpl userDetails = new UserDetailsImpl(user);

            String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
            String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);
            Long accessTokenExpiresIn = jwtTokenUtil.getAccessTokenExpiration(accessToken);

            return AuthResponse.success(accessToken, refreshToken, accessTokenExpiresIn, user);

        } catch (Exception e) {
            return AuthResponse.error(e.getMessage());
        }
    }

    
    @Transactional
    public AuthResponse refresh(String refreshToken) {
        try {
            User user = userService.getUserInformation(refreshToken);

            refreshTokenService.validateRefreshToken(refreshToken);

            UserDetailsImpl userDetails = new UserDetailsImpl(user);

            String newAccessToken = jwtTokenUtil.generateAccessToken(userDetails);
            Long accessTokenExpiresIn  = jwtTokenUtil.getAccessTokenExpiration(newAccessToken);

            return AuthResponse.success(newAccessToken, refreshToken, accessTokenExpiresIn, user);
        } catch (Exception e) {
            throw new InvalidTokenException("Erro: " + e.getMessage());
        }
    }

    @Transactional
    public void logout(String refreshToken) {
        if(refreshToken != null && !refreshToken.isEmpty()) {
            refreshTokenService.revokeToken(refreshToken);
        }
    }

    private User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null || !passwordUtil.matchesPasswords(password, user.getPasswordHash())) {
            throw new RuntimeException("Credenciais inválidas");
        }

        return user;
    }
}
