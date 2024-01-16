package br.com.gabrielferreira.contratos.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoMovimentacaoEnum {

    DEPOSITO("Déposito"),
    SAQUE("Saque");

    private final String descricao;

    public static boolean isSaque(TipoMovimentacaoEnum tipoMovimentacaoEnum){
        return SAQUE.name().equals(tipoMovimentacaoEnum.name());
    }
}
