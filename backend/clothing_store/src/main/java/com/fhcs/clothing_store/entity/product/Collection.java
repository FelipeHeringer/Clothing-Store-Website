package com.fhcs.clothing_store.entity.product;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "colecoes")
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer collectionId;

    @Column(name = "nome", length = 150, nullable = false)
    private String collectionName;

    @Column(name = "descricao", length = 255, nullable = false)
    private String description; 

    @Column(name = "lancamento", nullable = false)
    private LocalDate launchDate;

}
