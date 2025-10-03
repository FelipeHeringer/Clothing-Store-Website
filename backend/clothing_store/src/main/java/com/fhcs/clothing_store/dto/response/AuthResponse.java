package com.fhcs.clothing_store.dto.response;

import com.fhcs.clothing_store.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
    private UserDto safeUser;
    private boolean success;
    private String message;

    public static AuthResponse success(String accessToken, String refreshToken, long expiresIn, User user) {

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .safeUser(UserDto.fromUser(user))
                .success(true)
                .build();
    }

    public static AuthResponse error( String message) {

        return AuthResponse.builder()
            .success(false)
            .message(message)
            .build();
    }
}
