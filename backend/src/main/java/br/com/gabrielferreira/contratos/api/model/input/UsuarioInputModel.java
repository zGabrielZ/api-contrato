package br.com.gabrielferreira.contratos.api.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioInputModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(max = 255, min = 1)
    private String nome;

    @NotBlank
    @Size(max = 255, min = 1)
    private String sobrenome;

    @NotBlank
    @Email
    private String email;

    @Valid
    @NotEmpty
    @NotNull
    private List<TelefoneInputModel> telefones = new ArrayList<>();

    @Valid
    @NotEmpty
    @NotNull
    private List<PerfilInputModel> perfis = new ArrayList<>();
}
