package com.fhcs.clothing_store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressPatchDto {

    private Integer id;
    private String street;
    private Integer number;
    private String city;
    private String state;
    private String cep;
    private String complement;
    private String description;
    
}
