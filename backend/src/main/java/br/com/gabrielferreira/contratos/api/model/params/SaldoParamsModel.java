package br.com.gabrielferreira.contratos.api.model.params;

import br.com.gabrielferreira.contratos.api.validator.EnumValid;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoMovimentacaoEnum;
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
public class SaldoParamsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal valorInicial;

    private BigDecimal valorFinal;

    @EnumValid(enumClass = TipoMovimentacaoEnum.class)
    private String tipoMovimentacao;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;
}
