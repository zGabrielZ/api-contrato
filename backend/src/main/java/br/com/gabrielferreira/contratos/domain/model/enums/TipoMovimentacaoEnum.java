package br.com.gabrielferreira.contratos.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoMovimentacaoEnum {

    DEPOSITO("Déposito"),
    SAQUE("Saque");

    private final String descricao;
}
