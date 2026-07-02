package com.fhcs.clothing_store.infrastructure.out.persistence.entity;

import java.time.Instant;

import com.fhcs.clothing_store.core.domain.bo.image.ImageType;
import com.fhcs.clothing_store.infrastructure.out.persistence.entity.product.Product;
import com.fhcs.clothing_store.infrastructure.out.persistence.entity.product.variation.ProductVariation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "imagens_produtos")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_variacao", referencedColumnName = "id")
    private ProductVariation variation;

    @Column(columnDefinition = "TEXT", name = "url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20)
    private ImageType type;

    @Column(name = "posicao")
    private Integer position;

    @Column(name = "criado_em")
    private Instant createdAt;
    
}
