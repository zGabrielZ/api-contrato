package br.com.gabrielferreira.contratos.api.model.input;

import br.com.gabrielferreira.contratos.api.validator.EnumValid;
import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class TelefoneInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 2)
    @Pattern(regexp = "^[0-9]+$")
    private String ddd;

    @NotBlank
    @Size(max = 9, min = 8)
    @Pattern(regexp = "^[0-9]+$")
    private String numero;

    @Size(max = 255, min = 1)
    private String descricao;

    @NotNull
    @EnumValid(enumClass = TipoTelefoneEnum.class)
    private String tipoTelefone;
}
