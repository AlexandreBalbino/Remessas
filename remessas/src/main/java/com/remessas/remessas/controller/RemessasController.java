package com.remessas.remessas.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remessas.remessas.dto.remessa.RemessaDto;
import com.remessas.remessas.exception.ValidacaoRequestException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/remessa")
public class RemessasController extends BaseController {

    @PostMapping("/{emailRemetente}")
    public ResponseEntity<String> criarPessoaFisica(@PathVariable String emailRemetente,
            @Valid @RequestBody RemessaDto remessaDto,
            BindingResult bindingResult) throws ValidacaoRequestException {
        if (bindingResult.hasErrors()) {
            throw new ValidacaoRequestException(bindingResult.getAllErrors());
        }

        return ResponseEntity.ok("Remessa realizada com sucesso");
    }
}
