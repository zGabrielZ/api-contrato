package br.com.gabrielferreira.contratos.api.model.params;

import jakarta.validation.constraints.Size;
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
public class PerfilParamsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(max = 255, min = 1)
    private String descricao;

    @Size(max = 255, min = 1)
    private String autoriedade;
}
