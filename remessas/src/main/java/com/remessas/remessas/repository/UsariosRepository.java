package com.remessas.remessas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remessas.remessas.entity.Usuario;

public interface UsariosRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEmailOrCpfCnpj(String email, String cpfCnpj);

}
