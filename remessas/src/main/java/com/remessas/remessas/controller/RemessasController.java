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
import com.remessas.remessas.service.RemessasService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/remessa")
public class RemessasController extends BaseController {

    final RemessasService remessasService;

    @PostMapping("/{emailRemetente}")
    public ResponseEntity<String> criarRemessa(@PathVariable String emailRemetente,
            @Valid @RequestBody RemessaDto remessaDto,
            BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new ValidacaoRequestException(bindingResult.getAllErrors());
        }

        remessasService.criarRemessa(emailRemetente, remessaDto);

        return ResponseEntity.ok("Remessa realizada com sucesso");
    }
}
