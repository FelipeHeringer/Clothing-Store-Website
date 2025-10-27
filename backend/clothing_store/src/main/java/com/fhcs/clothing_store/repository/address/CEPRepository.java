package com.fhcs.clothing_store.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.address.CEP;

public interface CEPRepository extends JpaRepository<CEP, Integer> {

    public CEP findByCepNumber(String cepNumber);
}
