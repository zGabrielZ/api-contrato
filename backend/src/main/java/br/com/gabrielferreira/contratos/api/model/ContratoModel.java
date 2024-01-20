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
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContratoModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long numero;

    private LocalDate data;

    private BigDecimal valor;

    private Integer numeroDeParcelas;

    private String situacao;

    private List<ParcelaModel> parcelas = new ArrayList<>();

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;


}
