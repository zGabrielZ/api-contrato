package br.com.gabrielferreira.contratos.api.model.input;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContratoInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long numero;

    @NotNull
    @FutureOrPresent
    private LocalDate data;

    @NotNull
    @Positive
    @Digits(integer = 10, fraction = 2)
    private BigDecimal valor;

    @NotNull
    @Positive
    private Integer numeroDeParcelas;


}
