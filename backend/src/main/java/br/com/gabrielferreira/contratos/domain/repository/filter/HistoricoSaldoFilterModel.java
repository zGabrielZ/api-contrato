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
public class HistoricoSaldoFilterModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private BigDecimal valorAtualInicial;

    private BigDecimal valorAtualFinal;

    private BigDecimal quantidadeInformadaInicial;

    private BigDecimal quantidadeInformadaFinal;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;

    public boolean isIdExistente(){
        return this.id != null;
    }

    public boolean isValorAtualInicialExistente(){
        return this.valorAtualInicial != null;
    }

    public boolean isValorAtualFinalExistente(){
        return this.valorAtualFinal != null;
    }

    public boolean isQuantidadeInformadaInicialExistente(){
        return this.quantidadeInformadaInicial != null;
    }

    public boolean isQuantidadeInformadaFinalExistente(){
        return this.quantidadeInformadaFinal != null;
    }

    public boolean isDataCadastroExistente(){
        return this.dataCadastro != null;
    }

    public boolean isDataAtualizacaoExistente(){
        return this.dataAtualizacao != null;
    }
}
