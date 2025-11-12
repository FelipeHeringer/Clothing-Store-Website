package com.fhcs.clothing_store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fhcs.clothing_store.dto.request.AddressRequest;
import com.fhcs.clothing_store.dto.request.PrivateIndividualRequest;
import com.fhcs.clothing_store.dto.response.AddressDto;
import com.fhcs.clothing_store.dto.response.AddressResponse;
import com.fhcs.clothing_store.dto.response.PrivateIndividualResponse;
import com.fhcs.clothing_store.entity.PrivateIndividual;
import com.fhcs.clothing_store.service.PrivateIndividualService;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping("api/private-individuals")
public class PrivateIndividualController {

    @Autowired
    private PrivateIndividualService service;

    @PostMapping
    public ResponseEntity<PrivateIndividualResponse> createPrivateIndividual(
            @RequestHeader("Authorization") String token,
            @RequestBody PrivateIndividualRequest request) {

        try {
            String accessToken = token.substring(7);

            PrivateIndividual privateIndividual = service.createPrivateIndividual(accessToken, request);

            if (privateIndividual == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(PrivateIndividualResponse.error("Falha ao criar pessoa física."));
            }

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(PrivateIndividualResponse.success(privateIndividual, "Pessoa física criada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(PrivateIndividualResponse.error("Erro ao criar pessoa física: " + e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<PrivateIndividualResponse> getPrivateIndividualInformation(
            @RequestHeader("Authorization") String token) {

        try {
            String accessToken = token.substring(7);
            PrivateIndividual privateIndividual = service.getPrivateIndividualByToken(accessToken);

            if (privateIndividual == null) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(PrivateIndividualResponse.error("Credenciais inválidas."));
            }

            return ResponseEntity.status(HttpStatus.OK)
                    .body(PrivateIndividualResponse.success(privateIndividual,
                            "Pessoa física recuperada com sucesso!"));
        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(PrivateIndividualResponse.error("Erro ao recuperar pessoa física: " + ex.getMessage()));
        }
    }

    @PatchMapping("/me")
    public ResponseEntity<PrivateIndividualResponse> updatePrivateIndividualInformation(
            @RequestHeader("Authorization") String token,
            @RequestBody JsonPatch patch) {

        try {
            String accessToken = token.substring(7);

            PrivateIndividual newIndividual = service.updatePrivateIndividualInformation(accessToken, patch);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(PrivateIndividualResponse.success(newIndividual, "Indivíduo atualizado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(PrivateIndividualResponse.error("Erro ao atualizar indivíduo: " + e.getMessage()));
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<PrivateIndividualResponse> deletePrivateIndividual(
            @RequestHeader("Authorization") String token) {

        try {
            String accessToken = token.substring(7);

            service.deletePrivateIndividual(accessToken);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(PrivateIndividualResponse.messageOnly("Indivíduo deletado com sucesso!", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(PrivateIndividualResponse.error("Erro ao deletar indivíduo: " + e.getMessage()));
        }
    }

    @PostMapping("/me/addresses")
    public ResponseEntity<AddressResponse> addAddressToPrivateIndividual(@RequestHeader("Authorization") String token,
            @RequestBody AddressRequest addressRequest) {

        String accessToken = token.substring(7);

        List<AddressDto> addresses = service.addAddressToPrivateIndividual(accessToken, addressRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(AddressResponse.success(addresses, "Endereço criado com sucesso"));
    }

    @GetMapping("/me/addresses")
    public ResponseEntity<AddressResponse> getAddressesOfPrivateIndividual(
            @RequestHeader("Authorization") String token) {

        try {
            String accessToken = token.substring(7);

            List<AddressDto> addresses = service.getAddressesOfPrivateIndividual(accessToken);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(AddressResponse.success(addresses, "Endereços recuperados com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AddressResponse.error("Erro ao recuperar endereços: " + e.getMessage()));
        }
    }

    @PatchMapping("/me/addresses/{addressId}")
    public ResponseEntity<AddressResponse> updateAddressOfPrivateIndividual(
            @RequestHeader("Authorization") String token,
            @RequestBody JsonPatch patch, @PathVariable Integer addressId) {
        try {
            String accessToken = token.substring(7);

            List<AddressDto> addresses = service.updateAddressOfPrivateIndividual(accessToken, patch, addressId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(AddressResponse.success(addresses, "Endereço atualizado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AddressResponse.error("Erro ao atualizar o endereço: " + e.getMessage()));
        }

    }

    @DeleteMapping("/me/addresses/{addressId}")
    public ResponseEntity<AddressResponse> deleteAddressOfPrivateIndividual(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer addressId) {

        try {

            String accessToken = token.substring(7);

            List<AddressDto> addresses = service.deleteAddressOfPrivateIndividual(accessToken, addressId);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(AddressResponse.success(addresses, "Endereço deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(AddressResponse.error("Erro ao deletar o endereço: " + e.getMessage()));
        }
    }
}
