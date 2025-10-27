package com.fhcs.clothing_store.entity.address;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "enderecos")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer addressId;

    @ManyToOne
    @JoinColumn(name = "id_cep", referencedColumnName = "id")
    private CEP cep;

    @ManyToOne
    @JoinColumn(name = "id_cidade", referencedColumnName = "id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "id_estado", referencedColumnName = "id")
    private State state;

    @Column(name = "nome_rua", length = 150, nullable = false)
    private String streetName;

    @Column(name = "numero", nullable = false)
    private Integer number;

    @Column(name = "complemento", length = 150)
    private String complement;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<IndividualAddress> individualAddress = new ArrayList<>();
}
