package br.com.gabrielferreira.contratos.api.model.params;

import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneParamsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[0-9]+$")
    private String ddd;

    @Size(max = 9, min = 8)
    @Pattern(regexp = "^[0-9]+$")
    private String numero;

    @Size(max = 255, min = 1)
    private String descricao;

    private TipoTelefoneEnum tipoTelefone;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;
}
