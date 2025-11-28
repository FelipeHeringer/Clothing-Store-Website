package com.fhcs.clothing_store.entity.product;

import java.math.BigDecimal;

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
@Table(name = "produtos")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer productId;

    @ManyToOne
    @JoinColumn(name = "id_colecao", referencedColumnName = "id")
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Category category;
    
    @Column(name = "nome", length = 150, nullable = false)
    private String name;

    @Column(name = "descricao", length = 255, nullable = false)
    private String description;

    @Column(name = "valor", nullable = false, columnDefinition = "DEFAULT 0.00")
    private BigDecimal price;

    @Column(name = "avaliacao")
    private Integer score;
    
}
