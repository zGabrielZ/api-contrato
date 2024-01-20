package br.com.gabrielferreira.contratos.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParcelaModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalDate data;

    private BigDecimal valor;

    private String situacao;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
