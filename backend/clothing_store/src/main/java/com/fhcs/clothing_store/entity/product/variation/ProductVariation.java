package com.fhcs.clothing_store.entity.product.variation;

import com.fhcs.clothing_store.entity.product.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "produtos_variacoes")
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer variationId;

    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_cor", referencedColumnName = "id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "id_tamanho", referencedColumnName = "id")
    private Size size;

    @Column(name = "codigo_sku")
    private String skuCode;

    @Column(name = "quantidade")
    private Integer quantity;
}
