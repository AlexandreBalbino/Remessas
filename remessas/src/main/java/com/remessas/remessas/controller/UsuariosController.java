package com.remessas.remessas.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remessas.remessas.dto.usuario.CriarPessoaFisicaUsuarioDto;
import com.remessas.remessas.dto.usuario.CriarPessoaJuridicaUsuarioDto;
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.exception.UsuarioExistenteException;
import com.remessas.remessas.exception.ValidacaoRequestException;
import com.remessas.remessas.service.UsuariosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/usuario")
public class UsuariosController extends BaseController {

    final UsuariosService usuarioService;

    @GetMapping(value = "/{id}")
    public Usuario obtemUsuario(@PathVariable(value = "id") Long id) {
        return usuarioService.obtemUsuario(id);
    }

    @PostMapping("/pf")
    public ResponseEntity<String> criarPessoaFisica(@Valid @RequestBody CriarPessoaFisicaUsuarioDto criarUsuarioDto,
            BindingResult bindingResult) throws UsuarioExistenteException, ValidacaoRequestException {

        if (bindingResult.hasErrors()) {
            throw new ValidacaoRequestException(bindingResult.getAllErrors());
        }

        usuarioService.salvaUsuarioPessoFisica(criarUsuarioDto);
        return ResponseEntity.ok("User criado com sucesso");
    }

    @PostMapping("/pj")
    public ResponseEntity<String> criarPessoaJuridica(@Valid @RequestBody CriarPessoaJuridicaUsuarioDto criarUsuarioDto,
            BindingResult bindingResult)
            throws UsuarioExistenteException, NoSuchAlgorithmException, ValidacaoRequestException {
        if (bindingResult.hasErrors()) {
            throw new ValidacaoRequestException(bindingResult.getAllErrors());
        }

        usuarioService.salvaUsuarioPessoJuridica(criarUsuarioDto);
        return ResponseEntity.ok("User criado com sucesso");
    }
}
