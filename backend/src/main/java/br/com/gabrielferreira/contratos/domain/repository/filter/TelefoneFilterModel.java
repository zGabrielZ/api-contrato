package br.com.gabrielferreira.contratos.domain.repository.filter;

import br.com.gabrielferreira.contratos.domain.model.enums.TipoTelefoneEnum;
import io.micrometer.common.util.StringUtils;
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
public class TelefoneFilterModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String ddd;

    private String numero;

    private String descricao;

    private TipoTelefoneEnum tipoTelefone;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;

    public boolean isIdExistente(){
        return this.id != null;
    }

    public boolean isDddExistente(){
        return StringUtils.isNotBlank(this.ddd);
    }

    public boolean isNumeroExistente(){
        return StringUtils.isNotBlank(this.numero);
    }

    public boolean isDescricaoExistente(){
        return StringUtils.isNotBlank(this.descricao);
    }

    public boolean isTipoTelefoneExistente(){
        return this.tipoTelefone != null;
    }

    public boolean isDataCadastroExistente(){
        return this.dataCadastro != null;
    }

    public boolean isDataAtualizacaoExistente(){
        return this.dataAtualizacao != null;
    }
}
