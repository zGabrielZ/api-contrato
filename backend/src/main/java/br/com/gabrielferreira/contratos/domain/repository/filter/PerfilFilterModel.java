package br.com.gabrielferreira.contratos.domain.repository.filter;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PerfilFilterModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String descricao;

    private String autoriedade;

    public boolean isIdExistente(){
        return this.id != null;
    }

    public boolean isDescricaoExistente(){
        return StringUtils.isNotBlank(this.descricao);
    }

    public boolean isAutoriedadeExistente(){
        return StringUtils.isNotBlank(this.autoriedade);
    }
}
