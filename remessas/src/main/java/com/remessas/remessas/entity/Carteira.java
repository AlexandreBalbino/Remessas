package com.remessas.remessas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.remessas.remessas.enums.Origem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "carteira")
@Getter
public class Carteira {
    @Id
    private Long id;

    @Column(nullable = false)
    private Double saldo;

    @Column(nullable = false, length = 2)
    @Enumerated(value = EnumType.STRING)
    private Origem origem;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

}
