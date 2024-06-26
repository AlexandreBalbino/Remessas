package com.remessas.remessas.entity;

import java.util.List;

import com.remessas.remessas.enums.Origem;
import com.remessas.remessas.util.SegurancaSenhaUtil;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String cpfCnpj;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Carteira> carteiras;

    @OneToMany(mappedBy = "usuarioRemetente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Remessa> remessaRemetente;

    @OneToMany(mappedBy = "usuarioDestinario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Remessa> remessaUsuario;

    public Boolean isCpf() {
        return cpfCnpj.length() == 11;
    }

    public Boolean isCnpj() {
        return cpfCnpj.length() == 14;
    }

    public void criptografaSenha() {
        this.senha = SegurancaSenhaUtil.criptografaSenha(senha);
    }

    public Carteira getCarteiraPt() {
        return carteiras.stream().filter(x -> x.getOrigem() == Origem.PT)
                .findFirst().get();
    }

    public Carteira getCarteiraEn() {
        return carteiras.stream().filter(x -> x.getOrigem() == Origem.EN)
                .findFirst().get();
    }

}
