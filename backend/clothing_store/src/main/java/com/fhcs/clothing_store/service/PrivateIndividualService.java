package com.fhcs.clothing_store.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.request.PrivateIndividualRequest;
import com.fhcs.clothing_store.dto.PrivateIndividualPatchDto;
import com.fhcs.clothing_store.entity.PrivateIndividual;
import com.fhcs.clothing_store.entity.User;
import com.fhcs.clothing_store.entity.address.Address;
import com.fhcs.clothing_store.repository.PrivateIndividualRepository;
import com.fhcs.clothing_store.repository.UserRepository;
import com.fhcs.clothing_store.util.JsonPatchUtil;
import com.fhcs.clothing_store.util.JwtTokenUtil;
import com.github.fge.jsonpatch.JsonPatch;

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

    @Autowired
    private JsonPatchUtil jsonPatchUtil;

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

    public PrivateIndividual getPrivateIndividualByToken(String accessToken) {
        User user = userRepository.findByUsername(jwtTokenUtil.extractUsername(accessToken));
        return individualRepository.findByUser_UserId(user.getUserId());
    }

    public PrivateIndividual updatePrivateIndividualInformation(String accessToken, JsonPatch patch) {

        try {
            PrivateIndividual privateIndividual = getPrivateIndividualByToken(accessToken);

            PrivateIndividualPatchDto patchedIndividual = jsonPatchUtil.extractPatchedFields(patch,
                    PrivateIndividualPatchDto.class);
            applyChangesToIndividual(privateIndividual, patchedIndividual);

            return individualRepository.save(privateIndividual);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar informações do indivíduo: " + e.getMessage());
        }
    }

    private void applyChangesToIndividual(PrivateIndividual privateIndividual, PrivateIndividualPatchDto patchedIndividual) {

        try {
            verifyPhoneNumberUniqueness(patchedIndividual.getPhoneNumber(), privateIndividual.getIndividualId());
            if (patchedIndividual.getIndividualName() != null) {
                privateIndividual.setIndividualName(patchedIndividual.getIndividualName());
            }
            if (patchedIndividual.getPhoneNumber() != null) {
                String formattedPhoneNumber = patchedIndividual.getPhoneNumber().replaceAll("[^\\d]", "");
                privateIndividual.setPhoneNumber(formattedPhoneNumber);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao aplicar alterações no individuo: " + e.getMessage());
        }
    }

    private void verifyPhoneNumberUniqueness(String phoneNumber, Integer individualId) {

        String formattedPhoneNumber = phoneNumber.replaceAll("[^\\d]", "");

        boolean exists = individualRepository.existsByPhoneNumberAndIndividualIdNot(formattedPhoneNumber, individualId);

        if (exists) {
            throw new IllegalArgumentException("O número de telefone já está em uso por outro indivíduo.");
        }

    }

}
