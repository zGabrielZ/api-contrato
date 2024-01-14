package br.com.gabrielferreira.contratos.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_PERFIL")
public class Perfil implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "AUTORIEDADE", nullable = false, unique = true)
    private String autoriedade;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "perfis")
    private List<Usuario> usuarios = new ArrayList<>();

    public boolean isContemPerfil(List<Perfil> perfis){
        return perfis.stream().anyMatch(p -> p.getId().equals(this.id));
    }

    public boolean isNaoContemPerfil(List<Perfil> perfis){
        return !isContemPerfil(perfis);
    }
}
