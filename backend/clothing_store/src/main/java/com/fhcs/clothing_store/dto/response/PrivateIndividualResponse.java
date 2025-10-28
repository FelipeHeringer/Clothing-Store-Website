package com.fhcs.clothing_store.dto.response;

import com.fhcs.clothing_store.entity.PrivateIndividual;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PrivateIndividualResponse {

    private PrivateIndividualDto safeIndividual;
    private boolean success;
    private String message;

    public static PrivateIndividualResponse success(PrivateIndividual individual, String message) {

        return PrivateIndividualResponse.builder()
                .safeIndividual(PrivateIndividualDto.fromIndividual(individual))
                .success(true)
                .message(message)
                .build();
    }

    public static PrivateIndividualResponse error(String message) {

        return PrivateIndividualResponse.builder()
                .success(false)
                .message(message)
                .build();
    }
}
