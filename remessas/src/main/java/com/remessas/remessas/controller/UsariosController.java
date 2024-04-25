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
import com.remessas.remessas.entity.Usuario;
import com.remessas.remessas.service.UsariosService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UsariosController {

    final UsariosService usuarioService;

    @GetMapping(value = "/{id}")
    public Usuario obtemUsuario(@PathVariable(value = "id") Long id) {
        return usuarioService.obtemUsuario(id);
    }

    @PostMapping("/pf")
    public ResponseEntity<String> criarPessoaFisica(@Valid @RequestBody CriarPessoaFisicaUsuarioDto criarUsuarioDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
        }

        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/pj")
    public ResponseEntity<String> criarPessoaJuridica(@Valid @RequestBody CriarPessoaFisicaUsuarioDto criarUsuarioDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation errors: " + bindingResult.getAllErrors());
        }

        return ResponseEntity.ok("User created successfully");
    }
}
