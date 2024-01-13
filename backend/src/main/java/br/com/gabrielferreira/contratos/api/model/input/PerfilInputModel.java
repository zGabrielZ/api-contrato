package br.com.gabrielferreira.contratos.api.model.input;

import jakarta.validation.constraints.NotNull;
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
public class PerfilInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;
}
