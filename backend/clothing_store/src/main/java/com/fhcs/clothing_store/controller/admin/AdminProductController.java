package com.fhcs.clothing_store.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fhcs.clothing_store.dto.request.product.ProductRequest;
import com.fhcs.clothing_store.dto.response.product.ProductResponse;
import com.fhcs.clothing_store.entity.product.Product;
import com.fhcs.clothing_store.service.admin.AdminProductService;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class AdminProductController {

    @Autowired
    private AdminProductService adminProductService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@RequestHeader("Authorization") String token,
            @RequestBody ProductRequest productRequest) {
        try {
            Product product = adminProductService.createProduct(productRequest);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ProductResponse.success(product, "Produto criado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductResponse.error("Erro ao criar o produto: " + e.getMessage()));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer productId) {
        try {
            Product product = adminProductService.getProductById(productId);
            return ResponseEntity.status(HttpStatus.OK).body(ProductResponse.success(product, "Produto encontrado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ProductResponse.error("Produto não encontrado: " + e.getMessage()));
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer productId,
            @RequestBody JsonPatch patch) {
        try {
            Product product = adminProductService.updateProduct(productId, patch);

            return ResponseEntity.status(HttpStatus.OK).body(ProductResponse.success(product, "Produto atualizado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductResponse.error("Erro ao atualizar o produto: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Integer productId) {
        try {
            adminProductService.deleteProduct(productId);

            return ResponseEntity.status(HttpStatus.OK).body(ProductResponse.messageOnly(true, "Produto deletado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductResponse.error("Erro ao deletar o produto: " + e.getMessage()));
        }
    }
}
