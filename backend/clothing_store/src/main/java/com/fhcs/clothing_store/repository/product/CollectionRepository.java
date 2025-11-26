package com.fhcs.clothing_store.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.product.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    
}
