package com.fhcs.clothing_store.dto.response;

import com.fhcs.clothing_store.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {

    private Integer userId;
    private String username;
    private String email;

    public static UserDto fromUser(User user) {

        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
