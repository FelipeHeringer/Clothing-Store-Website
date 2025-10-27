package com.fhcs.clothing_store.repository.address;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.address.Address;

public interface AddressRepository  extends JpaRepository<Address, Integer>{
    
}
