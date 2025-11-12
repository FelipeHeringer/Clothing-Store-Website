package com.fhcs.clothing_store.repository.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fhcs.clothing_store.entity.address.Address;
import com.fhcs.clothing_store.entity.address.IndividualAddress;

public interface AddressRepository  extends JpaRepository<Address, Integer>{
    
    @Query("SELECT ia FROM Address a JOIN a.individualAddress ia WHERE a.addressId = :addressId")
    List<IndividualAddress> findIndividualAddressesByAddressId(@Param("addressId") Integer addressId);

    @Query("SELECT ia FROM Address a JOIN a.individualAddress ia WHERE ia.privateIndividual.individualId = :individualId")
    List<IndividualAddress> findIndividualAddressesByIndividualId(@Param("individualId") Integer individualId);
}
