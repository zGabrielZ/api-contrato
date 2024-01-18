package br.com.gabrielferreira.contratos.domain.repository.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaldoFilterModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal valorInicial;

    private BigDecimal valorFinal;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;

    public boolean isIdExistente(){
        return this.id != null;
    }

    public boolean isValorInicialExistente(){
        return this.valorInicial != null;
    }

    public boolean isValorFinalExistente(){
        return this.valorFinal != null;
    }

    public boolean isDataCadastroExistente(){
        return this.dataCadastro != null;
    }

    public boolean isDataAtualizacaoExistente(){
        return this.dataAtualizacao != null;
    }
}
