package com.fhcs.clothing_store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.AddressPatchDto;
import com.fhcs.clothing_store.dto.request.AddressRequest;
import com.fhcs.clothing_store.dto.response.ViaCepResponse;
import com.fhcs.clothing_store.entity.PrivateIndividual;
import com.fhcs.clothing_store.entity.address.Address;
import com.fhcs.clothing_store.entity.address.CEP;
import com.fhcs.clothing_store.entity.address.City;
import com.fhcs.clothing_store.entity.address.IndividualAddress;
import com.fhcs.clothing_store.entity.address.State;
import com.fhcs.clothing_store.exception.AddressNotFoundException;
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
            throw new AddressNotFoundException("CEP não encontrado.");
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

    public List<IndividualAddress> getIndividualAddresses(Integer individualId) {
        return addressRepository.findIndividualAddressesByIndividualId(individualId);
    }

    public Address updateAddressInformation(Address address, AddressPatchDto addressPatched) {

        applyChangesToAddress(addressPatched, address);

        return addressRepository.save(address);
    }

    public void deleteIndividualAddress(IndividualAddress individualAddress) {
        IndividualAddressRepository.delete(individualAddress);
    }

    public void applyChangesToIndividualAddress(IndividualAddress individualAddress, AddressPatchDto addressPatched) {
        if (addressPatched.getDescription() != null) {
            individualAddress.setDescription(addressPatched.getDescription());
            IndividualAddressRepository.save(individualAddress);
        }
    }

    private void applyChangesToAddress(AddressPatchDto addressPatched, Address address) {

        if (addressPatched.getCep() != null) {
            String cleanCep = addressPatched.getCep().replace("-", "");
            ViaCepResponse viaCepResponse = viaCepService.fetchAddressByCep(cleanCep);

            if (viaCepResponse.getError() != null) {
                throw new AddressNotFoundException("CEP não encontrado.");
            }

            try {
                validateChangesWithViaCepResponse(addressPatched, viaCepResponse);
                CEP cep = findOrCreateCEP(cleanCep);
                address.setCep(cep);

            } catch (Exception e) {
                throw new RuntimeException("Erro ao aplicar mudanças ao endereço: " + e.getMessage());
            }
        }

        if (addressPatched.getStreet() != null) {
            address.setStreetName(addressPatched.getStreet());
        }

        if (addressPatched.getNumber() != null) {
            address.setNumber(addressPatched.getNumber());
        }

        if (addressPatched.getCity() != null) {
            City city = cityRepository.findByCityName(addressPatched.getCity());
            address.setCity(city);
        }

        if (addressPatched.getState() != null) {
            State state = stateRepository.findByUf(addressPatched.getState());
            address.setState(state);
        }

        if (addressPatched.getComplement() != null) {
            address.setComplement(addressPatched.getComplement());
        }
    }

    private void validateChangesWithViaCepResponse(AddressPatchDto addressPatched,
            ViaCepResponse viaCepResponse) {

        StringBuilder errors = new StringBuilder();

        if ((addressPatched.getStreet() != null &&
                !addressPatched.getStreet().equalsIgnoreCase(viaCepResponse.getStreet()))
                || addressPatched.getStreet() == null) {
            errors.append("O nome da rua não corresponde ao CEP fornecido. Rua esperada: ")
                    .append(viaCepResponse.getStreet())
                    .append(". ");
        }

        if ((addressPatched.getCity() != null &&
                !addressPatched.getCity().equalsIgnoreCase(viaCepResponse.getCity()))
                || addressPatched.getCity() == null) {
            errors.append("A cidade não corresponde ao CEP fornecido. Cidade esperada: ")
                    .append(viaCepResponse.getCity())
                    .append(". ");
        }

        if ((addressPatched.getState() != null &&
                !addressPatched.getState().equalsIgnoreCase(viaCepResponse.getState()))
                || addressPatched.getState() == null) {
            errors.append("O estado não corresponde ao CEP fornecido. Estado esperado")
                    .append(viaCepResponse.getState())
                    .append(". ");
        }

        if (errors.length() > 0) {
            throw new AddressValidationException("Falha na validação do Endereço: " + errors.toString());
        }

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
