package com.fhcs.clothing_store.entity.address;

import com.fhcs.clothing_store.entity.PrivateIndividual;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@Entity
@Table(name = "enderecos_pessoas")
@NoArgsConstructor
@AllArgsConstructor
public class IndividualAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer individualAddressId;

    @ManyToOne
    @JoinColumn(name = "id_endereco", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private PrivateIndividual privateIndividual;

    @Column(name = "descricao", length = 200, nullable = false, columnDefinition = "VARCHAR(200) DEFAULT 'Casa'")
    private String description;
}
