package com.fhcs.clothing_store.repository.product.variation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.product.variation.Size;

public interface SizeRepository extends JpaRepository<Size,Integer> {
    
}
