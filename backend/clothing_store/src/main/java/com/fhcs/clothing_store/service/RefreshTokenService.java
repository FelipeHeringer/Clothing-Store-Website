package com.fhcs.clothing_store.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fhcs.clothing_store.entity.RefreshToken;
import com.fhcs.clothing_store.entity.User;
import com.fhcs.clothing_store.exception.InvalidTokenException;
import com.fhcs.clothing_store.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenDurationMs;

    @Transactional
    public RefreshToken createRefreshToken(User user, String token) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(Instant.now().plusMillis(refreshTokenDurationMs))
                .revoked(false)
                .createdAt(Instant.now())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public void revokeToken(String token) {
        refreshTokenRepository.revokeToken(token, Instant.now());
    }

    public void validateRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidTokenException("Refresh Token não encontrado"));

        if (refreshToken.isRevoked()) {
            throw new InvalidTokenException("Refresh Token revogado");
        }

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);

            throw new InvalidTokenException("Refresh Token expirado");
        }
    }
}
