package br.com.gabrielferreira.contratos.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SituacaoParcelaEnum {

    EM_ANDAMENTO("Em andamento"),
    PAGO("Pago"),
    ATRASAOD("Atrasado");

    private final String descricao;
}
