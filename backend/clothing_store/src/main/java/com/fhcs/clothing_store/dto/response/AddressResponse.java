package com.fhcs.clothing_store.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AddressResponse {

    private String message;
    private boolean success;
    private List<AddressDto> addresses;   
    
    public static AddressResponse success (List<AddressDto> addresses, String message) {
        return AddressResponse.builder()
                .message(message)
                .success(true)
                .addresses(addresses)
                .build();
    }

    public static AddressResponse error (String message) {
        return AddressResponse.builder()
                .message(message)
                .success(false)
                .build();
    }
}
