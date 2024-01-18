package br.com.gabrielferreira.contratos.domain.model;

import br.com.gabrielferreira.contratos.domain.model.enums.SituacaoContratoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.UTC;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"parcelas", "usuario"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_CONTRATO")
public class Contrato implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NUMERO", nullable = false)
    private Long numero;

    @Column(name = "DATA", nullable = false)
    private LocalDate data;

    @Column(name = "VALOR_TOTAL", nullable = false)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    private SituacaoContratoEnum situacaoContrato;

    @OneToMany(mappedBy = "contrato", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Parcela> parcelas = new ArrayList<>();

    @JoinColumn(name = "ID_USUARIO", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
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
}
