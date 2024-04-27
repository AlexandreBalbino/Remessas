package com.remessas.remessas.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SegurancaSenhaUtilTests {

    @Test
    void Retorna_Sucesso_Caso_Senha_Igual_Criptografada() {
        String senha = "123";
        String senhaCriptografada = "202cb962ac59075b964b07152d234b70";
        var senhaGerada = SegurancaSenhaUtil.criptografaSenha(senha);

        assertEquals(senhaGerada, senhaCriptografada);
    }

}
