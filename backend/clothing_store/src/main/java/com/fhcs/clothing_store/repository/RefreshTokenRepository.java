package com.fhcs.clothing_store.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fhcs.clothing_store.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{

    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.revoked = true, rt.revokedAt = :revokeAt where rt.token = :token")
    void revokeToken(String token, Instant revokeAt);

    Optional<RefreshToken> findByToken(String token);
    
}
