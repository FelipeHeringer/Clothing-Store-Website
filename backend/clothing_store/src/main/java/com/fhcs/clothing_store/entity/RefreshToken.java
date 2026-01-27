package com.fhcs.clothing_store.entity;

import java.time.Instant;

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

@Setter
@Getter
@Builder
@Entity
@Table(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "token", nullable = false)
    private String token;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    @Column(name = "data_expiracao", nullable = false)
    private Instant expiryDate;

    @Column(name = "revogado", nullable = false)
    private boolean revoked = false;

    @Column(name = "criado_em", nullable = false)
    private Instant createdAt;

    @Column(name = "revogado_em")
    private Instant revokedAt;

    public boolean isExpired() {
        return Instant.now().isAfter(this.expiryDate);
    }
}
