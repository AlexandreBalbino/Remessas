package com.remessas.remessas.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.remessas.remessas.util.DataUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemessasService {

    private static final int SUPERIOR_LIMITE = 1;
    private static final int SALDO_INSUFICIENTE = -1;
    private static final int LIMITE_TRANSACIONADO_PF = 10000;
    private static final int LIMITE_TRANSACIONADO_PJ = 50000;
    private final UsuariosRepository usuariosRepository;
    private final RemessasRepository remessaRepository;

    @Transactional
    public Remessa criarRemessa(String emailRemetente, RemessaDto remessaDto) throws Exception {

        var usuarioRemetente = obtemUsuarioPorEmail(emailRemetente);
        var usuarioDestinario = obtemUsuarioPorEmail(remessaDto.getEmailDestinario());

        validaCarteiraPtRemetente(remessaDto, usuarioRemetente);
        validaTotalRemessasDiaria(remessaDto, usuarioRemetente);

        calculaCarteiraPtRemetente(remessaDto, usuarioRemetente.getCarteiraPt());
        calculaCarteiraEnDestinatario(remessaDto, usuarioDestinario.getCarteiraEn());

        salvaUsuarios(usuarioRemetente, usuarioDestinario);
        var remessa = salvaRemessa(remessaDto, usuarioRemetente, usuarioDestinario);
        return remessa;
    }

    private void validaCarteiraPtRemetente(RemessaDto remessaDto, Usuario usuarioRemetente)
            throws RemessaException {
        var carteiraPtRemetente = usuarioRemetente.getCarteiraPt();

        if (carteiraPtRemetente.getSaldo().compareTo(remessaDto.getRemessa()) == SALDO_INSUFICIENTE) {
            throw new RemessaException("Saldo do remetente insuficiente");
        }
    }

    private void validaTotalRemessasDiaria(RemessaDto remessaDto, Usuario usuarioRemetente)
            throws RemessaException {
        var remessasDeHoje = remessaRepository.findRemessaByUsuarioAndDataInicioAndDataFim(usuarioRemetente.getId(),
                DataUtil.obtemDataHojeInicial(),
                DataUtil.obtemDataHojeFinal());

        var somaRemessasUsuarioRemetenteDia = remessasDeHoje.stream().map(x -> x.getRemessa()).reduce(BigDecimal.ZERO,
                BigDecimal::add);

        var totalRemessasComRemessaAtual = somaRemessasUsuarioRemetenteDia.add(remessaDto.getRemessa());

        if (usuarioRemetente.isCpf()
                && totalRemessasComRemessaAtual.compareTo(new BigDecimal(LIMITE_TRANSACIONADO_PF)) == SUPERIOR_LIMITE) {
            throw new RemessaException(
                    "Limite diario de transação pessoa física excedido: R$ " + totalRemessasComRemessaAtual.toString());
        } else if (usuarioRemetente.isCnpj()
                && totalRemessasComRemessaAtual.compareTo(new BigDecimal(LIMITE_TRANSACIONADO_PJ)) == SUPERIOR_LIMITE) {
            throw new RemessaException(
                    "Limite diario de transação pessoa jurídica excedido: R$ "
                            + totalRemessasComRemessaAtual.toString());
        }
    }

    private Usuario obtemUsuarioPorEmail(String email) throws UsuarioInexistenteException {
        var usuarioDestinatarioOpcional = usuariosRepository.findByEmail(email);
        if (!usuarioDestinatarioOpcional.isPresent()) {
            throw new UsuarioInexistenteException("Não existe usuario com o email: " + email);
        }
        return usuarioDestinatarioOpcional.get();
    }

    private void calculaCarteiraPtRemetente(RemessaDto remessaDto, Carteira carteiraPtRemetente)
            throws RemessaException {

        carteiraPtRemetente.setSaldo(
                subtraiSaldoRemetente(carteiraPtRemetente.getSaldo(), remessaDto.getRemessa()));
    }

    private void calculaCarteiraEnDestinatario(RemessaDto remessaDto, Carteira carteiraEnDestinatario) {
        carteiraEnDestinatario.setSaldo(adicionaSaldoDestinatario(remessaDto, carteiraEnDestinatario));
    }

    private BigDecimal subtraiSaldoRemetente(BigDecimal saldoRemetente, BigDecimal remessa) {
        return saldoRemetente.subtract(remessa);
    }

    private BigDecimal adicionaSaldoDestinatario(RemessaDto remessaDto, Carteira carteiraEnDestinatario) {
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

        var remessa = Remessa.builder()
                .usuarioRemetente(usuarioRemetente)
                .usuarioDestinario(usuarioDestinario)
                .remessa(remessaDto.getRemessa())
                .dataRemessa(LocalDateTime.now())
                .build();

        return remessaRepository.saveAndFlush(remessa);
    }

}
