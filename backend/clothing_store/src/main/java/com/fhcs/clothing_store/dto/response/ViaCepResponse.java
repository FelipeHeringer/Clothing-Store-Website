package com.fhcs.clothing_store.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViaCepResponse {
    
    @JsonProperty("cep")
    private String cep;
    
    @JsonProperty("logradouro")
    private String street;
    
    @JsonProperty("complemento")
    private String complement;
    
    @JsonProperty("bairro")
    private String neighborhood;
    
    @JsonProperty("localidade")
    private String city;
    
    @JsonProperty("uf")
    private String state;
    
    @JsonProperty("ibge")
    private String ibgeCode;
    
    @JsonProperty("erro")
    private Boolean error;
}
