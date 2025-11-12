package com.fhcs.clothing_store.dto.response;

import com.fhcs.clothing_store.entity.address.Address;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class AddressDto {
    private Integer id;
    private String street;
    private Integer number;
    private String city;
    private String state;
    private String cep;
    private String complement;
    private String description;

    public static AddressDto fromAddress(Address address, String description) {
        String cep = address.getCep().getCepNumber();
        String formattedCep = cep.replaceAll("(\\d{5})", "$1-");

        String complement = address.getComplement() != null ? address.getComplement() : "";
    
        return AddressDto.builder()
                .id(address.getAddressId())
                .street(address.getStreetName())
                .number(address.getNumber())
                .city(address.getCity().getCityName())
                .state(address.getState().getStateName())
                .cep(formattedCep)
                .complement(complement)
                .description(description)
                .build();

    }
    
}
