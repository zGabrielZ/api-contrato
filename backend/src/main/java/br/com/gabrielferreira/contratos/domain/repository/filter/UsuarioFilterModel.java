package br.com.gabrielferreira.contratos.domain.repository.filter;

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
public class UsuarioFilterModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private LocalDate dataCadastro;

    private LocalDate dataAtualizacao;

    public boolean isIdExistente(){
        return this.id != null;
    }

    public boolean isNomeExistente(){
        return StringUtils.isNotBlank(this.nome);
    }

    public boolean isSobrenomeExistente(){
        return StringUtils.isNotBlank(this.sobrenome);
    }

    public boolean isEmailExistente(){
        return StringUtils.isNotBlank(this.email);
    }

    public boolean isDataCadastroExistente(){
        return this.dataCadastro != null;
    }

    public boolean isDataAtualizacaoExistente(){
        return this.dataAtualizacao != null;
    }
}
