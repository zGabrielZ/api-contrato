package br.com.gabrielferreira.contratos.api.model.input;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaldoInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Positive
    private BigDecimal valor;
}
