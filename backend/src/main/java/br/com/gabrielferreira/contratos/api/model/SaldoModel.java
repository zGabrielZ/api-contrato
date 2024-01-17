package br.com.gabrielferreira.contratos.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaldoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal valor;

    private String tipoMovimentacao;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;

    private UsuarioResumidoModel usuario;
}
