package com.remessas.remessas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remessas.remessas.entity.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByEmailOrCpfCnpj(String email, String cpfCnpj);

    Optional<Usuario> findByEmail(String email);

}
