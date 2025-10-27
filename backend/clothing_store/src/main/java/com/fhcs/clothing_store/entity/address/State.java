package com.fhcs.clothing_store.entity.address;

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
@Table(name = "estados")
@NoArgsConstructor
@AllArgsConstructor
public class State {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer stateId;

    @Column(name = "nome_estado", length = 100, nullable = false, unique = true)
    private String stateName;

    @Column(name = "uf", length = 2, nullable = false, unique = true)
    private String uf;
}
