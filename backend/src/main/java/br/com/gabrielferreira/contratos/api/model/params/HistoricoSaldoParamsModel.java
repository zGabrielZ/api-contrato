package br.com.gabrielferreira.contratos.api.model.params;

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
public class HistoricoSaldoParamsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal valorAtualInicial;

    private BigDecimal valorAtualFinal;

    private BigDecimal quantidadeInformadaInicial;

    private BigDecimal quantidadeInformadaFinal;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;
}
