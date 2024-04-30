package com.remessas.remessas.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "remessa")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Remessa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal remessa;

    @Column(nullable = false)
    private BigDecimal cotacaoDolar;

    @Column(nullable = false)
    private LocalDateTime dataRemessa;

    @ManyToOne
    @JoinColumn(name = "id_usuario_remetente", nullable = false)
    @JsonIgnore
    private Usuario usuarioRemetente;

    @ManyToOne
    @JoinColumn(name = "id_usuario_destinatario", nullable = false)
    @JsonIgnore
    private Usuario usuarioDestinario;

}
