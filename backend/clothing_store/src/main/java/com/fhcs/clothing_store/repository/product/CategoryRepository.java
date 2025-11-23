package com.fhcs.clothing_store.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fhcs.clothing_store.entity.product.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
