package br.com.gabrielferreira.contratos.domain.model;

import br.com.gabrielferreira.contratos.domain.model.enums.SituacaoParcelaEnum;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"contrato"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Parcela implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;

    private LocalDate data;

    private BigDecimal valor;

    private SituacaoParcelaEnum situacao;

    private Contrato contrato;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
