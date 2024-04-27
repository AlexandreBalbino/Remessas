package com.remessas.remessas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.remessas.remessas.dto.usuario.CriarPessoaFisicaUsuarioDto;
import com.remessas.remessas.exception.UsuarioExistenteException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/remessa")
public class RemessasController extends BaseController {

    @PostMapping("/{emailRemetente}")
    public ResponseEntity<String> criarPessoaFisica(@RequestParam String email,
            @Valid @RequestBody CriarPessoaFisicaUsuarioDto criarUsuarioDto,
            BindingResult bindingResult) throws UsuarioExistenteException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Erro na validação: " +
                    bindingResult.getAllErrors());
        }

        return ResponseEntity.ok("User criado com sucesso");
    }
}
