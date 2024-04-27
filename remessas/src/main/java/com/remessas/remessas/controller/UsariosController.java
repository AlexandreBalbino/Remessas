package com.remessas.remessas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remessas.remessas.dto.CriarPessoaFisicaUsuarioDto;
import com.remessas.remessas.dto.CriarPessoaJuridicaUsuarioDto;
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.exception.UsuarioExistenteException;
import com.remessas.remessas.service.UsariosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UsariosController extends BaseController {

    final UsariosService usuarioService;

    @GetMapping(value = "/{id}")
    public Usuario obtemUsuario(@PathVariable(value = "id") Long id) {
        return usuarioService.obtemUsuario(id);
    }

    @PostMapping("/pf")
    public ResponseEntity<String> criarPessoaFisica(@Valid @RequestBody CriarPessoaFisicaUsuarioDto criarUsuarioDto,
            BindingResult bindingResult) throws UsuarioExistenteException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Erro na validação: " + bindingResult.getAllErrors());
        }

        usuarioService.saveUsuarioPessoFisica(criarUsuarioDto);
        return ResponseEntity.ok("User criado com sucesso");
    }

    @PostMapping("/pj")
    public ResponseEntity<String> criarPessoaJuridica(@Valid @RequestBody CriarPessoaJuridicaUsuarioDto criarUsuarioDto,
            BindingResult bindingResult) throws UsuarioExistenteException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Erro na validação: " + bindingResult.getAllErrors());
        }

        usuarioService.saveUsuarioPessoJuridica(criarUsuarioDto);
        return ResponseEntity.ok("User criado com sucesso");
    }
}
