package br.com.gabrielferreira.contratos.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String ddd;

    private String numero;

    private String descricao;

    private String tipoTelefone;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
