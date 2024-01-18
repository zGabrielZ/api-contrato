package br.com.gabrielferreira.contratos.domain.model;

import br.com.gabrielferreira.contratos.domain.model.enums.SituacaoParcelaEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.UTC;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"contrato"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_PARCELA")
public class Parcela implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "DATA", nullable = false)
    private LocalDate data;

    @Column(name = "VALOR", nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private SituacaoParcelaEnum situacaoParcela;

    @JoinColumn(name = "ID_CONTRATO", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Contrato contrato;

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
}
