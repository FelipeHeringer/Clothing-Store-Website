package com.fhcs.clothing_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fhcs.clothing_store.dto.response.ViaCepResponse;

@Service
public class ViaCepService {
    
    private static final String VIACEP_API_URL = "https://viacep.com.br/ws/";
    
    @Autowired
    private RestTemplate restTemplate;

    public ViaCepResponse fetchAddressByCep(String cep) {

        String url = VIACEP_API_URL + cep + "/json/";

        ViaCepResponse viaCepResponse = restTemplate.getForObject(url, ViaCepResponse.class);

        return viaCepResponse;
    }


}
