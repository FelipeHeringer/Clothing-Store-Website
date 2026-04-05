package com.fhcs.clothing_store.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fhcs.clothing_store.dto.request.product.variation.ProductVariationRequest;
import com.fhcs.clothing_store.dto.response.product.variation.ProductVariationResponse;
import com.fhcs.clothing_store.entity.product.Product;
import com.fhcs.clothing_store.entity.product.variation.ProductVariation;
import com.fhcs.clothing_store.service.admin.AdminProductService;
import com.fhcs.clothing_store.service.admin.AdminProductVariationService;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping("/api/admin/products/{productId}/variations")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class AdminProductVariationController {

    @Autowired
    private AdminProductService productService;

    @Autowired
    private AdminProductVariationService productVariationService;

    @PostMapping
    public ResponseEntity<ProductVariationResponse> createProductVariation(@PathVariable Integer productId,
            @RequestBody ProductVariationRequest request) {

        try {
            Product product = productService.getProductById(productId);
            ProductVariation productVariation = productVariationService.createProductVariation(product, request);

            return ResponseEntity.ok(ProductVariationResponse.success(product, productVariation, "Variação de produto criada com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductVariationResponse.error("Erro ao criar a variação de produto: " + e.getMessage()));
        }

    }

    @PatchMapping("/{variationId}")
    public ResponseEntity<ProductVariationResponse> updateProductVariation(@PathVariable Integer productId,
            @PathVariable Integer variationId, @RequestBody JsonPatch patch) {

        try {
            ProductVariation productVariation = productVariationService.getProductVariationById(variationId);
            ProductVariation updateProductVariation = productVariationService.updateProductVariation(patch, productVariation);

            return ResponseEntity.ok(ProductVariationResponse.success(productVariation.getProduct(), updateProductVariation, "Variação de produto atualizada com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductVariationResponse.error("Erro ao atualizar a variação de produto: " + e.getMessage()));
        }

    }

    @DeleteMapping("/{variationId}")
    public ResponseEntity<ProductVariationResponse> deleteProductVariation(@PathVariable Integer variationId) {
        try {
            ProductVariation productVariation = productVariationService.getProductVariationById(variationId);
            productVariationService.deleteProductVariation(productVariation);

            return ResponseEntity.ok(ProductVariationResponse.messageOnly(true, "Variação de produto deletada com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProductVariationResponse.error("Erro ao deletar a variação de produto: " + e.getMessage()));
        }
    }
}
