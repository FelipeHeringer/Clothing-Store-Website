package com.fhcs.clothing_store.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.address.IndividualAddress;

public interface IndividualAddressRepository extends JpaRepository<IndividualAddress, Integer> {
    
}
