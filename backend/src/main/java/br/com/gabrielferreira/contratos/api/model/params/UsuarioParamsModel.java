package br.com.gabrielferreira.contratos.api.model.params;

import jakarta.validation.constraints.Email;
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
public class UsuarioParamsModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(max = 255, min = 1)
    private String nome;

    @Size(max = 255, min = 1)
    private String sobrenome;

    @Email
    private String email;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;
}
