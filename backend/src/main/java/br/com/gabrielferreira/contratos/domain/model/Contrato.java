package br.com.gabrielferreira.contratos.domain.model;

import br.com.gabrielferreira.contratos.domain.model.enums.SituacaoContratoEnum;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"parcelas", "usuario"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contrato implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;

    private Long numero;

    private LocalDate data;

    private BigDecimal valorTotal;

    private SituacaoContratoEnum situacao;

    private List<Parcela> parcelas = new ArrayList<>();

    private Usuario usuario;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
