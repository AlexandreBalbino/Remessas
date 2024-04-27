package com.remessas.remessas.dto.remessa;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RemessaDto {

    @NotNull(message = "E-mail destinatário é obrigatório")
    @Email(message = "E-mail deve ser válido")
    private String emailDestinario;

    @NotNull(message = "Valor remessa é obrigatório")
    @DecimalMin(value = "0.0", message = "Valor de remessa deve ser positivo")
    private BigDecimal remessa;

}
