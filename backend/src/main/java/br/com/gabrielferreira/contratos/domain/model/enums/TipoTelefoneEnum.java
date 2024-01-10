package br.com.gabrielferreira.contratos.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TipoTelefoneEnum {

    RESIDENCIAL("Residencial"),
    CELULAR("Celular");

    private final String descricao;
}
