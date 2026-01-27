package com.fhcs.clothing_store.dto.request;

import lombok.Getter;

@Getter
public class LogoutRequest {
    
    private String refreshToken;
}
