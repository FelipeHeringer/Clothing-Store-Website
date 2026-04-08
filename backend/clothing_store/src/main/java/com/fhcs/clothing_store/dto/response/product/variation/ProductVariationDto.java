package com.fhcs.clothing_store.dto.response.product.variation;
 
import com.fhcs.clothing_store.entity.product.variation.ProductVariation;
import lombok.Builder;
import lombok.Getter;
 
import java.math.BigDecimal;
 
@Builder
@Getter
public class ProductVariationDto {
 
    private Integer variationId;
    private Integer productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private Integer score;
    private String categoryName;
    private String collectionName;
    private String color;
    private String size;
    private String skuCode;
    private Integer stock;
    private boolean inStock;
 
    public static ProductVariationDto fromVariation(ProductVariation v) {
        return ProductVariationDto.builder()
                .variationId(v.getVariationId())
                .productId(v.getProduct().getProductId())
                .productName(v.getProduct().getName())
                .productDescription(v.getProduct().getDescription())
                .price(v.getProduct().getPrice())
                .score(v.getProduct().getScore())
                .categoryName(v.getProduct().getCategory().getCategoryName())
                .collectionName(v.getProduct().getCollection().getCollectionName())
                .color(v.getColor().getColor())
                .size(v.getSize().getSize())
                .skuCode(v.getSkuCode())
                .stock(v.getStock())
                .inStock(v.getStock() > 0)
                .build();
    }
}
 