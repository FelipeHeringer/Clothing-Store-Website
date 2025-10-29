package com.fhcs.clothing_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.request.AddressRequest;
import com.fhcs.clothing_store.dto.response.ViaCepResponse;
import com.fhcs.clothing_store.entity.PrivateIndividual;
import com.fhcs.clothing_store.entity.address.Address;
import com.fhcs.clothing_store.entity.address.CEP;
import com.fhcs.clothing_store.entity.address.City;
import com.fhcs.clothing_store.entity.address.IndividualAddress;
import com.fhcs.clothing_store.entity.address.State;
import com.fhcs.clothing_store.exception.AddressValidationException;
import com.fhcs.clothing_store.repository.address.AddressRepository;
import com.fhcs.clothing_store.repository.address.CEPRepository;
import com.fhcs.clothing_store.repository.address.CityRepository;
import com.fhcs.clothing_store.repository.address.StateRepository;
import com.fhcs.clothing_store.repository.address.IndividualAddressRepository;

@Service
public class AddressService {

    @Autowired
    private ViaCepService viaCepService;

    @Autowired
    private IndividualAddressRepository IndividualAddressRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CEPRepository cepRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    public Address createAddress(AddressRequest request) {

        ViaCepResponse viaCepResponse = viaCepService.fetchAddressByCep(request.getCep());

        if (viaCepResponse.getError() != null) {
            throw new RuntimeException("CEP não encontrado.");
        }

        validateAddress(request, viaCepResponse);

        City city = cityRepository.findByCityName(request.getCity());

        State state = stateRepository.findByUf(request.getStateUF());

        CEP cep = findOrCreateCEP(request.getCep());

        Address address = Address.builder()
                .cep(cep)
                .state(state)
                .city(city)
                .streetName(request.getStreetName())
                .number(request.getNumber())
                .complement(request.getComplement() != null && !request.getComplement().isBlank()
                        ? request.getComplement()
                        : null)
                .build();

        return addressRepository.save(address);
    }

    public IndividualAddress linkIndividualAndAddress(PrivateIndividual individual, Address address,
            String description) {

        IndividualAddress individualAddress = IndividualAddress.builder()
                .privateIndividual(individual)
                .address(address)
                .description(description)
                .build();

        return IndividualAddressRepository.save(individualAddress);
    }

    private void validateAddress(AddressRequest request, ViaCepResponse viaCepResponse) {

        StringBuilder errors = new StringBuilder();

        if (!request.getStreetName().equalsIgnoreCase(viaCepResponse.getStreet())) {
            errors.append("O nome da rua não corresponde ao CEP fornecido. Rua esperada: ")
                    .append(viaCepResponse.getStreet())
                    .append(". ");
        }

        if (!request.getCity().equalsIgnoreCase(viaCepResponse.getCity())) {
            errors.append("A cidade não corresponde ao CEP fornecido. Cidade esperada: ")
                    .append(viaCepResponse.getCity())
                    .append(". ");
        }

        if (!request.getStateUF().equalsIgnoreCase(viaCepResponse.getState())) {
            errors.append("O estado não corresponde ao CEP fornecido. Estado esperado")
                    .append(viaCepResponse.getState())
                    .append(". ");
        }

        if (errors.length() > 0) {
            throw new AddressValidationException("Falha na validação do Endereço: " + errors.toString());
        }

    }

    private CEP findOrCreateCEP(String cepNumber) {
        String cleanCepNumber = cepNumber.replace("-", "");
        CEP cep = cepRepository.findByCepNumber(cleanCepNumber);

        if (cep == null) {
            cep = new CEP();
            cep.setCepNumber(cleanCepNumber);
            cep = cepRepository.save(cep);
        }

        return cep;
    }
}
