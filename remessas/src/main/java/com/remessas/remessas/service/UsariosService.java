package com.remessas.remessas.service;

import org.springframework.stereotype.Service;

import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.repository.UsariosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsariosService {

    final UsariosRepository usuarioRepository;

    public Usuario obtemUsuario(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public Usuario saveUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

}
