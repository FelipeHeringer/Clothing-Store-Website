package com.fhcs.clothing_store.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhcs.clothing_store.dto.ProductVariationPatchDto;
import com.fhcs.clothing_store.dto.request.product.variation.ProductVariationRequest;
import com.fhcs.clothing_store.entity.product.Product;
import com.fhcs.clothing_store.entity.product.variation.Color;
import com.fhcs.clothing_store.entity.product.variation.ProductVariation;
import com.fhcs.clothing_store.entity.product.variation.Size;
import com.fhcs.clothing_store.repository.product.variation.ColorRepository;
import com.fhcs.clothing_store.repository.product.variation.ProductVariationRepository;
import com.fhcs.clothing_store.repository.product.variation.SizeRepository;
import com.fhcs.clothing_store.util.JsonPatchUtil;
import com.github.fge.jsonpatch.JsonPatch;

@Service
public class AdminProductVariationService {

    @Autowired
    private ProductVariationRepository productVariationRepository;

    @Autowired
    private ColorRepository colorReposiory;

    @Autowired 
    private SizeRepository sizeRepository;

    @Autowired
    private JsonPatchUtil jspnPatchUtil;


    public ProductVariation createProductVariation(Product product, ProductVariationRequest request) {
        Color  color = colorReposiory.findById(request.getColorId()).orElseThrow();
        Size size = sizeRepository.findById(request.getSizeId()).orElseThrow();

        ProductVariation productVariation = new ProductVariation();
        productVariation.setColor(color);
        productVariation.setSize(size);
        productVariation.setProduct(product);
        productVariation.setSkuCode(request.getSkuCode());

        return productVariationRepository.save(productVariation);
    }

    public ProductVariation getProductVariationById(Integer variationId) {
        return productVariationRepository.findById(variationId).orElseThrow();
    }

    public ProductVariation updateProductVariation(JsonPatch patch, ProductVariation productVariation) {
        try {
            ProductVariationPatchDto ProductVariationPatch = jspnPatchUtil.extractPatchedFields(patch, ProductVariationPatchDto.class);
            applyChangesToProductVariation(ProductVariationPatch, productVariation);
            return productVariationRepository.save(productVariation);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void deleteProductVariation(ProductVariation productVariation) {
        try {
            productVariationRepository.delete(productVariation);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private void applyChangesToProductVariation(ProductVariationPatchDto productVariationPatch,
            ProductVariation productVariation) {

        if (productVariationPatch.getColorId() != null) {
            Color color = colorReposiory.findById(productVariationPatch.getColorId()).orElseThrow();
            productVariation.setColor(color);
        }

        if (productVariationPatch.getSizeId() != null) {
            Size size = sizeRepository.findById(productVariationPatch.getSizeId()).orElseThrow();
            productVariation.setSize(size);
        }
       
        if (productVariationPatch.getSkuCode() != null) {
            productVariation.setSkuCode(productVariationPatch.getSkuCode());
        }

        // Implement Image URL later

        // if (productVariationPatch.getImageUrl() != null) {
        //     productVariation.setImageUrl(productVariationPatch.getImageUrl());
        // }

    }
    
    
}
