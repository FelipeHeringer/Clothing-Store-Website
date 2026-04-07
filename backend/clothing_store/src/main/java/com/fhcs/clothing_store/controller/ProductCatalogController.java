package com.fhcs.clothing_store.controller;
 
import com.fhcs.clothing_store.dto.response.product.variation.ProductVariationDto;
import com.fhcs.clothing_store.repository.product.variation.ProductVariationRepository;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("/api/catalog")
public class ProductCatalogController {
 
    @Autowired
    private ProductVariationRepository variationRepository;
 
    /**
     * GET /api/catalog/variations
     *
     * Query params (all optional):
     *   categoryId  – filter by category
     *   collectionId – filter by collection
     *   colorId     – filter by color
     *   sizeId      – filter by size
     *   inStock     – true = only in-stock items
     *   page, size, sort (Spring Pageable)
     *
     * Example: GET /api/catalog/variations?categoryId=1&inStock=true&page=0&size=20&sort=price,asc
     */
    @GetMapping("/variations")
    public PagedModel<ProductVariationDto> getVariations(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) Integer collectionId,
            @RequestParam(required = false) Integer colorId,
            @RequestParam(required = false) Integer sizeId,
            @RequestParam(required = false) Boolean inStock,
            @PageableDefault(size = 20) Pageable pageable) {
 
        Specification<com.fhcs.clothing_store.entity.product.variation.ProductVariation> spec =
                Specification.where(null);
 
        if (categoryId != null) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("product").get("category").get("categoryId"), categoryId));
        }
        if (collectionId != null) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("product").get("collection").get("collectionId"), collectionId));
        }
        if (colorId != null) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("color").get("colorId"), colorId));
        }
        if (sizeId != null) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("size").get("sizeId"), sizeId));
        }
        if (Boolean.TRUE.equals(inStock)) {
            spec = spec.and((root, q, cb) ->
                    cb.greaterThan(root.get("stock"), 0));
        }
 
        Page<ProductVariationDto> page = variationRepository.findAll(spec, pageable)
                .map(ProductVariationDto::fromVariation);
 
        return new PagedModel<>(page);
    }
 
    /**
     * GET /api/catalog/variations/{variationId}
     * Returns a single variation — used on the product detail page.
     */
    @GetMapping("/variations/{variationId}")
    public ProductVariationDto getVariationById(@PathVariable Integer variationId) {
        return variationRepository.findById(variationId)
                .map(ProductVariationDto::fromVariation)
                .orElseThrow(() -> new IllegalArgumentException("Variação não encontrada: " + variationId));
    }
}