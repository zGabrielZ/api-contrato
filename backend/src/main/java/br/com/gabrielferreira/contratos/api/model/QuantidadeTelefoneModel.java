package br.com.gabrielferreira.contratos.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuantidadeTelefoneModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long quantidadeDeTelefone;
}
