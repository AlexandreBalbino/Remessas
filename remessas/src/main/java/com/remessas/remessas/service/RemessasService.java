package com.remessas.remessas.service;

import org.springframework.stereotype.Service;

import com.remessas.remessas.dto.remessa.RemessaDto;
import com.remessas.remessas.entity.Remessa;
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.enums.Origem;
import com.remessas.remessas.exception.RemessaException;
import com.remessas.remessas.exception.UsuarioInexistenteException;
import com.remessas.remessas.repository.UsuariosRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemessasService {

    private static final int SALDO_INSUFICIENTE = -1;
    final UsuariosRepository usuariosRepository;

    public Remessa criarRemessa(String emailRemetente, RemessaDto remessaDto) throws Exception {

        var usuarioRemetente = obtemUsuarioPorEmail(emailRemetente);

        var usuarioDestinario = obtemUsuarioPorEmail(remessaDto.getEmailDestinario());

        var carteiraPtRemetente = usuarioRemetente.getCarteiras().stream().filter(x -> x.getOrigem() == Origem.PT)
                .findFirst().get();

        if (carteiraPtRemetente.getSaldo().compareTo(remessaDto.getRemessa()) == SALDO_INSUFICIENTE) {
            throw new RemessaException("Saldo do remetente insuficiente");
        }

        return new Remessa();
    }

    private Usuario obtemUsuarioPorEmail(String email) throws UsuarioInexistenteException {
        var usuarioDestinatarioOpcional = usuariosRepository.findByEmail(email);
        if (!usuarioDestinatarioOpcional.isPresent()) {
            throw new UsuarioInexistenteException("Não existe usuario com o email: " + email);
        }
        return usuarioDestinatarioOpcional.get();
    }
}
