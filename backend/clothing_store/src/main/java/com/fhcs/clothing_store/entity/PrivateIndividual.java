package com.fhcs.clothing_store.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fhcs.clothing_store.entity.address.IndividualAddress;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pessoas_fisicas")
@NoArgsConstructor
@AllArgsConstructor
public class PrivateIndividual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer individualId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private User user;

    @Column(name = "nome_pessoa", length = 150, nullable = false)
    private String individualName;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate birthDate;

    @Column(name = "CPF", length = 11, nullable = false, unique = true)
    private String CPF;

    @Column(name = "telefone", length = 11, nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "privateIndividual", cascade = CascadeType.ALL)
    private List<IndividualAddress> individualAddress = new ArrayList<>();

}