package com.fhcs.clothing_store.repository.product.variation;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.product.variation.ProductVariation;

public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer>{
    
}
