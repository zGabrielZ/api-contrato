package br.com.gabrielferreira.contratos.domain.model;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"telefones", "perfis", "contratos"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private List<Telefone> telefones = new ArrayList<>();

    private List<Perfil> perfis = new ArrayList<>();

    private List<Contrato> contratos = new ArrayList<>();

    private ZonedDateTime dataCadastro;

    private ZonedDateTime dataAtualizacao;
}
