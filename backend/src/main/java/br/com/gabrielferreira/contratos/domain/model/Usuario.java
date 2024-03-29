package br.com.gabrielferreira.contratos.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static br.com.gabrielferreira.contratos.common.utils.DataUtils.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"telefones", "perfis", "saldoTotal", "saldos", "historicoSaldos", "contratos"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "NOME", nullable = false)
    private String nome;

    @Column(name = "SOBRENOME", nullable = false)
    private String sobrenome;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Telefone> telefones = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TB_USUARIO_PERFIL",
            joinColumns = @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID", table = "TB_USUARIO"),
            inverseJoinColumns = @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID", table = "TB_PERFIL"))
    private List<Perfil> perfis = new ArrayList<>();

    @JoinColumn(name = "ID_SALDO_TOTAL", nullable = false)
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private SaldoTotalUsuario saldoTotal;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Saldo> saldos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<HistoricoSaldo> historicoSaldos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Contrato> contratos = new ArrayList<>();

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
