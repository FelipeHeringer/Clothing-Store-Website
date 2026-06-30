package com.fhcs.clothing_store.infrastructure.in.rest.controller.admin;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fhcs.clothing_store.application.port.in.service.ImageServicePort;
import com.fhcs.clothing_store.application.port.in.service.admin.AdminProductServicePort;
import com.fhcs.clothing_store.core.domain.bo.image.ProductImageBO;
import com.fhcs.clothing_store.core.domain.bo.image.ProductImageRequestBO;
import com.fhcs.clothing_store.core.domain.bo.product.ProductBO;
import com.fhcs.clothing_store.infrastructure.in.rest.dto.ProductPatchDto;
import com.fhcs.clothing_store.infrastructure.in.rest.dto.request.ProductImagesRequest;
import com.fhcs.clothing_store.infrastructure.in.rest.dto.request.product.ProductRequest;
import com.fhcs.clothing_store.infrastructure.in.rest.dto.response.product.ProductImageDto;
import com.fhcs.clothing_store.infrastructure.in.rest.dto.response.product.ProductImageResponse;
import com.fhcs.clothing_store.infrastructure.in.rest.dto.response.product.ProductResponse;
import com.fhcs.clothing_store.infrastructure.in.rest.dto.response.product.variation.ProductVariationDto;
import com.fhcs.clothing_store.infrastructure.in.rest.mapper.ProductDtoMapper;
import com.fhcs.clothing_store.infrastructure.in.rest.mapper.ProductImageRequestMapper;
import com.fhcs.clothing_store.util.JsonPatchUtil;
import com.github.fge.jsonpatch.JsonPatch;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
public class AdminProductControllerAdapter {

    private final AdminProductServicePort productService;
    private final JsonPatchUtil jsonPatchUtil;
    private final ProductDtoMapper productMapper;
    private final ImageServicePort imageServicePort;

    public AdminProductControllerAdapter(AdminProductServicePort productService,
            JsonPatchUtil jsonPatchUtil, ProductDtoMapper productMapper, ImageServicePort imageServicePort) {
        this.productService = productService;
        this.jsonPatchUtil = jsonPatchUtil;
        this.productMapper = productMapper;
        this.imageServicePort = imageServicePort;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@jakarta.validation.Valid @RequestBody ProductRequest request) {
        try {
            ProductBO product = productService.createProduct(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(productMapper.toProductResponse(product, "Produto criado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductResponse.error("Erro ao criar o produto: " + e.getMessage()));
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Integer productId) {
        try {
            ProductBO product = productService.getProductById(productId);
            return ResponseEntity.ok(
                    productMapper.toProductResponse(product, "Produto encontrado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ProductResponse.error("Produto não encontrado: " + e.getMessage()));
        }
    }

    @GetMapping
    public PagedModel<ProductVariationDto> getAllProducts(@PageableDefault Pageable pageable) {
        return new PagedModel<>(productService.getAllProducts(pageable)
                .map(bo -> {
                    ProductVariationDto.ProductVariationDtoBuilder builder = ProductVariationDto.builder()
                            .productId(bo.getProductId())
                            .productName(bo.getName())
                            .productDescription(bo.getDescription())
                            .price(bo.getPrice())
                            .score(bo.getScore())
                            .images(bo.getImages() != null
                                    ? bo.getImages().stream().map(ProductImageDto::from).toList()
                                    : List.of());
                    if (bo.getCategory() != null)
                        builder.categoryName(bo.getCategory().getCategoryName());
                    if (bo.getCollection() != null)
                        builder.collectionName(bo.getCollection().getCollectionName());
                    return builder.build();
                }));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Integer productId,
            @RequestBody JsonPatch patch) {
        try {
            ProductPatchDto patchDto = jsonPatchUtil.extractPatchedFields(patch, ProductPatchDto.class);
            ProductBO product = productService.updateProduct(productId, patchDto);
            return ResponseEntity.ok(
                    productMapper.toProductResponse(product, "Produto atualizado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductResponse.error("Erro ao atualizar o produto: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Integer productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok(
                    ProductResponse.messageOnly(true, "Produto deletado com sucesso."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ProductResponse.error("Erro ao deletar o produto: " + e.getMessage()));
        }
    }

    @PostMapping(value = "/images", consumes = "multipart/form-data")
    public ResponseEntity<ProductImageResponse> uploadImages(@jakarta.validation.Valid @ModelAttribute ProductImagesRequest request) {
        try {
            ProductImageRequestBO bo = ProductImageRequestMapper.toBO(request);
            List<ProductImageBO> productImages = imageServicePort.uploadProductImages(bo);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ProductImageResponse.success("Imagens do Produto criadas com sucesso", productImages));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ProductImageResponse.error("Erro ao criar as imagens do produto"));
        }
    }
}
