package com.fhcs.clothing_store.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.request.PrivateIndividualRequest;
import com.fhcs.clothing_store.entity.PrivateIndividual;
import com.fhcs.clothing_store.entity.User;
import com.fhcs.clothing_store.entity.address.Address;
import com.fhcs.clothing_store.repository.PrivateIndividualRepository;
import com.fhcs.clothing_store.repository.UserRepository;
import com.fhcs.clothing_store.util.JwtTokenUtil;

@Service
public class PrivateIndividualService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private PrivateIndividualRepository individualRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public PrivateIndividual createPrivateIndividual(String accessToken, PrivateIndividualRequest request) {

        User user = userRepository.findByUsername(jwtTokenUtil.extractUsername(accessToken));

        User requestUser = userRepository.findByEmail(request.getEmail());

        if (!requestUser.equals(user)) {
            throw new IllegalArgumentException("O email fornecido não corresponde ao usuário autenticado.");
        }

        String formattedCpf = request.getCpf().replaceAll("[^\\d]", "");
        String formattedPhoneNumber = request.getPhoneNumber().replaceAll("[^\\d]", "");

        PrivateIndividual individual = new PrivateIndividual();
        individual.setUser(requestUser);
        individual.setIndividualName(request.getIndividualName());
        individual.setBirthDate(LocalDate.parse(request.getBirthDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        individual.setCPF(formattedCpf);
        individual.setPhoneNumber(formattedPhoneNumber);

        individualRepository.save(individual);

        Address address = addressService.createAddress(request.getAddress());

        addressService.linkIndividualAndAddress(individual, address, request.getAddress().getDescription());

        return individual;
    }

}
