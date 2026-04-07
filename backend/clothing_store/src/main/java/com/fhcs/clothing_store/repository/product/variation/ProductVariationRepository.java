package com.fhcs.clothing_store.repository.product.variation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fhcs.clothing_store.entity.product.variation.ProductVariation;

// JpaSpecificationExecutor enables the dynamic filter in ProductCatalogController
public interface ProductVariationRepository extends JpaRepository<ProductVariation, Integer>,
        JpaSpecificationExecutor<ProductVariation> {

}
