package br.com.gabrielferreira.contratos.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private List<PerfilModel> perfis = new ArrayList<>();

    private ZonedDateTime dataCadastrado;

    private ZonedDateTime dataAtualizacao;
}
