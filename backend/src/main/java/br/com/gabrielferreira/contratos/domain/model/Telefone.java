package br.com.gabrielferreira.contratos.domain.model;

import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;
import static br.com.gabrielferreira.contratos.common.utils.MascaraUtils.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"usuario"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_TELEFONE")
public class Telefone implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DDD", nullable = false)
    private String ddd;

    @Column(name = "NUMERO", nullable = false)
    private String numero;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Enumerated(EnumType.STRING)
    private TipoTelefoneEnum tipoTelefone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @Column(name = "DATA_CADASTRO", nullable = false)
    private ZonedDateTime dataCadastro;

    @Column(name = "DATA_ATUALIZACAO")
    private ZonedDateTime dataAtualizacao;

    @PrePersist
    public void prePersist(){
        dataCadastro = ZonedDateTime.now(UTC);
    }

    @PreUpdate
    public void preUpdate(){
        dataAtualizacao = ZonedDateTime.now(UTC);
    }

    public boolean isResidencial(){
        return this.tipoTelefone.equals(TipoTelefoneEnum.RESIDENCIAL);
    }

    public boolean isCelular(){
        return this.tipoTelefone.equals(TipoTelefoneEnum.CELULAR);
    }

    public String getNumeroFormatado(){
        if(this.numero.length() == 8){
            return toMascaraTelefoneResidencial(this.ddd, this.numero);
        } else {
            return toMascaraTelefoneCelular(this.ddd, this.numero);
        }
    }

}
