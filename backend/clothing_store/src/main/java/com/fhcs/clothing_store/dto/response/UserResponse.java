package com.fhcs.clothing_store.dto.response;

import com.fhcs.clothing_store.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private boolean success;
    private String message;
    private UserDto safeUser;

    public static UserResponse success(User user, String message) {

        return UserResponse.builder()
                .success(true)
                .message(message)
                .safeUser(UserDto.fromUser(user))
                .build();
    }

    public static UserResponse error(String message) {

        return UserResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
