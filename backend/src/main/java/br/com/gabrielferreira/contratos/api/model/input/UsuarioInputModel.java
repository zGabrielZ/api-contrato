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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<TelefoneInputModel> telefones = new HashSet<>();

    @Valid
    @NotEmpty
    @NotNull
    private List<PerfilInputModel> perfis = new ArrayList<>();
}
