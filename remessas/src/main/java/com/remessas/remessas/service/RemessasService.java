package com.remessas.remessas.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.remessas.remessas.dto.remessa.RemessaDto;
import com.remessas.remessas.entity.Carteira;
import com.remessas.remessas.entity.Remessa;
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.exception.RemessaException;
import com.remessas.remessas.exception.UsuarioInexistenteException;
import com.remessas.remessas.repository.RemessasRepository;
import com.remessas.remessas.repository.UsuariosRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemessasService {

    private static final int SALDO_INSUFICIENTE = -1;
    private final UsuariosRepository usuariosRepository;
    private final RemessasRepository remessaRepository;

    @Transactional
    public Remessa criarRemessa(String emailRemetente, RemessaDto remessaDto) throws Exception {

        var usuarioRemetente = obtemUsuarioPorEmail(emailRemetente);
        var usuarioDestinario = obtemUsuarioPorEmail(remessaDto.getEmailDestinario());

        calculaCarteiraPtRemetente(remessaDto, usuarioRemetente);
        calculaCarteiraEnDestinatario(remessaDto, usuarioDestinario);

        salvaUsuarios(usuarioRemetente, usuarioDestinario);
        var remessa = salvaRemessa(remessaDto, usuarioRemetente, usuarioDestinario);
        return remessa;
    }

    private Usuario obtemUsuarioPorEmail(String email) throws UsuarioInexistenteException {
        var usuarioDestinatarioOpcional = usuariosRepository.findByEmail(email);
        if (!usuarioDestinatarioOpcional.isPresent()) {
            throw new UsuarioInexistenteException("Não existe usuario com o email: " + email);
        }
        return usuarioDestinatarioOpcional.get();
    }

    private void calculaCarteiraPtRemetente(RemessaDto remessaDto, Usuario usuarioRemetente) throws RemessaException {
        var carteiraPtRemetente = usuarioRemetente.getCarteiraPt();

        if (carteiraPtRemetente.getSaldo().compareTo(remessaDto.getRemessa()) == SALDO_INSUFICIENTE) {
            throw new RemessaException("Saldo do remetente insuficiente");
        }

        carteiraPtRemetente.setSaldo(
                calculaSaldoRemetente(carteiraPtRemetente.getSaldo(), remessaDto.getRemessa()));
    }

    private void calculaCarteiraEnDestinatario(RemessaDto remessaDto, Usuario usuarioDestinario) {
        var carteiraEnDestinatario = usuarioDestinario.getCarteiraEn();
        carteiraEnDestinatario.setSaldo(calculaSaldoDestinatario(remessaDto, carteiraEnDestinatario));
    }

    private BigDecimal calculaSaldoRemetente(BigDecimal saldoRemetente, BigDecimal remessa) {
        return saldoRemetente.subtract(remessa);
    }

    private BigDecimal calculaSaldoDestinatario(RemessaDto remessaDto, Carteira carteiraEnDestinatario) {
        var precisaoCalculo = new MathContext(3, RoundingMode.HALF_UP);
        var remessaConvertida = remessaDto.getRemessa().divide(new BigDecimal("5.16"), precisaoCalculo);
        return carteiraEnDestinatario.getSaldo().add(remessaConvertida);
    }

    private void salvaUsuarios(Usuario usuarioRemetente, Usuario usuarioDestinario) {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioRemetente);
        usuarios.add(usuarioDestinario);

        usuariosRepository.saveAllAndFlush(usuarios);
    }

    private Remessa salvaRemessa(RemessaDto remessaDto, Usuario usuarioRemetente, Usuario usuarioDestinario) {
        var date = new Date();

        var remessa = Remessa.builder()
                .usuarioRemetente(usuarioRemetente)
                .usuarioDestinario(usuarioDestinario)
                .remessa(remessaDto.getRemessa())
                .dataRemessa(new Timestamp(date.getTime()))
                .build();

        remessaRepository.save(remessa);
        return remessa;
    }

}
