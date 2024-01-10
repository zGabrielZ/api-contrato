package br.com.gabrielferreira.contratos.domain.model;

import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Telefone implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;

    private String ddd;

    private String numero;

    private String descricao;

    private TipoTelefoneEnum tipoTelefone;

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;

}
