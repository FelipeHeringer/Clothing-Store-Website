package com.fhcs.clothing_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.PrivateIndividual;

public interface PrivateIndividualRepository extends JpaRepository<PrivateIndividual, Integer> {
    
    PrivateIndividual findByUser_UserId(Integer userId);

    boolean existsByPhoneNumberAndIndividualIdNot(String formattedPhoneNumber, Integer individualId);
}
